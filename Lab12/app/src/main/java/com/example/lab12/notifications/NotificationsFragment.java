package com.example.lab12.notifications;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab12.R;
import com.example.lab12.databinding.FragmentHomeBinding;
import com.example.lab12.databinding.FragmentNotificationsBinding;
import com.example.lab12.viewmodel.ShareViewModel;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    private ShareViewModel shareViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        shareViewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);

        shareViewModel.getShareData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer data) {
                binding.textNotifications.setText(String.valueOf(data));
            }
        });
        return root;
         }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}