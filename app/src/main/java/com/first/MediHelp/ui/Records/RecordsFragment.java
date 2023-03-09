package com.first.MediHelp.ui.Records;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

//import com.first.MediHelp.databinding.FragmentSlideshowBinding;


public class RecordsFragment extends Fragment {

//    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RecordsViewModel slideshowViewModel =
                new ViewModelProvider(this).get(RecordsViewModel.class);
//
//        binding = FragmentrRcordsBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
//        final TextView textView = binding.textSlideshow;
//        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        binding = null;
    }
}