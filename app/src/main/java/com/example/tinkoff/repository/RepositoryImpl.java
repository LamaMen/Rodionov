package com.example.tinkoff.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tinkoff.App;
import com.example.tinkoff.repository.model.Gif;
import com.example.tinkoff.repository.model.GifJson;
import com.example.tinkoff.repository.model.GifList;
import com.example.tinkoff.view.MainActivity;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryImpl implements Repository {
    private static Repository instance;

    private final MutableLiveData<Gif> liveData = new MutableLiveData<>();
    Map<String, FragmentData> dataMap = new HashMap<>();
    private Observer observer;
    private String currentChanel;

    private RepositoryImpl() {
        // Задаем шаблоны для каналов
        for (int i = 0; i < MainActivity.chancels.length; i++) {
            dataMap.put(MainActivity.chancels[i], new FragmentData());
        }
    }

    public static Repository getInstance() {
        if (instance == null) {
            instance = new RepositoryImpl();
        }
        return instance;
    }

    public void loadNewGifs() {
        // Увидомляем о загрузке данных
        observer.loading();

        // С помощью Retrofit 2 получаем Json с адресами изображений от API
        App.getInstance().service.getGifs(currentChanel, dataMap.get(currentChanel)
                .getCurrentPage()).enqueue(new Callback<GifList>() {
            @Override
            public void onResponse(@NotNull Call<GifList> call, @NotNull Response<GifList> response) {
                if (response.isSuccessful()) {
                    GifList returnedPage = response.body();

                    // Если ничего не получили, добавляем последнюю гифку
                    if (returnedPage.results.size() == 0) {
                        dataMap.get(currentChanel).addGif(new Gif());
                    } else {
                        // Если ответ не пустой сохраняем в текущий канал пришедшие адреса изображений
                        for (GifJson json : returnedPage.results) {
                            dataMap.get(currentChanel).addGif(new Gif(json));
                        }
                    }

                    // Увидомляем фрагмент о том, что загрузка прошла успешно
                    observer.done();
                    // Возращаем изображения
                    updateGif();
                }
            }

            @Override
            public void onFailure(@NotNull Call<GifList> call, @NotNull Throwable t) {
                // При возникновении ошибки увидомляем об этом фрагмент и
                // возращаемся на прошлую коректную страницу загрузки
                observer.done();
                observer.error();
                dataMap.get(currentChanel).roilBackPage();
            }
        });
    }

    public LiveData<Gif> getLiveData() {
        return liveData;
    }

    @Override
    public void updateGif() {
        // Если мы не выходим за пределы загруженных изображений возращаем текщее,
        // иначе загражаем новую страницу
        FragmentData data = dataMap.get(currentChanel);
        if (data.getNumberCurrentGif() < data.getSize()) {
            liveData.setValue(data.getGif());
        } else {
            data.incrementPage();
            loadNewGifs();
        }
    }

    @Override
    public void nextGif() {
        dataMap.get(currentChanel).incrementNumberGif();
        updateGif();
    }

    @Override
    public void previousGif() {
        dataMap.get(currentChanel).roilBackNumberGif();
        liveData.setValue(dataMap.get(currentChanel).getGif());
    }

    @Override
    public boolean isFirst() {
        return dataMap.get(currentChanel).isFirstGif();
    }

    @Override
    public void connect(Observer observer, String chanel) {
        this.observer = observer;
        currentChanel = chanel;
    }

    // Интерфейс для того, чтобы репозиторий мог увидомлять фрагмент о возникновении событий
    public interface Observer {
        void error();

        void loading();

        void done();
    }
}
