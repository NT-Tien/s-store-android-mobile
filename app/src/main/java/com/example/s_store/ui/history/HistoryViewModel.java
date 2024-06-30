package com.example.s_store.ui.history;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.s_store.auth.network.TokenManager;
import com.example.s_store.common.models.OrderModel;
import com.example.s_store.di.order.OrderService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.Getter;

@HiltViewModel
public class HistoryViewModel extends ViewModel {

    private final OrderService orderService;
    private final TokenManager tokenManager;
    @Getter
    private final MutableLiveData<List<OrderModel>> list = new MutableLiveData<>(new ArrayList<>());

    @Inject
    public HistoryViewModel(OrderService orderService, TokenManager tokenManager) {
        this.orderService = orderService;
        this.tokenManager = tokenManager;
    }


    public void loadData() {
        new Thread(() -> {
            List<OrderModel> result = orderService.getOrders(tokenManager.getToken());
            System.out.println(result);
            this.list.postValue(result);
        }).start();
    }
}
