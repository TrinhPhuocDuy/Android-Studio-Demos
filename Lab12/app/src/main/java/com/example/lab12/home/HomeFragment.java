package com.example.lab12.home;

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
import com.example.lab12.viewmodel.ShareViewModel;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private ShareViewModel sharedViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       binding = FragmentHomeBinding.inflate(inflater, container, false);
       View root = binding.getRoot();

       sharedViewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);

       sharedViewModel.getShareData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
           @Override
           public void onChanged(Integer data) {
               binding.textHome.setText(String.valueOf(data));
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