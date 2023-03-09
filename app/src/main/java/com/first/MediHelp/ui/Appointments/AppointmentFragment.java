package com.first.MediHelp.ui.Appointments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.first.MediHelp.R;
//import com.first.MediHelp.databinding.FragmentGalleryBinding;


public class AppointmentFragment extends Fragment {
    private View view;
//    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        GalleryViewModel galleryViewModel =
//                new ViewModelProvider(this).get(GalleryViewModel.class);
//
//        binding = FragmentGalleryBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
//        final TextView textView = binding.textGallery;
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        view = inflater.inflate(R.layout.activity_appointment, container , false);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }
}