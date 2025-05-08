package net.nyx.label.printer;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.Locale;

public class MoreFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        TextView tv = view.findViewById(R.id.tv);
        tv.setText(Constant.All_TEMPLATE_LAYOUTS.length + "");
        view.findViewById(R.id.ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.sznyx.net/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        view.findViewById(R.id.layout_print_alignment).setOnClickListener(view1 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle(R.string.select_print_alignment);

            final String[] alignments = {
                    getString(R.string.print_alignment_left),
                    getString(R.string.print_alignment_center),
                    getString(R.string.print_alignment_right)
            };
            SharedPreferences prefs = getContext().getSharedPreferences("Settings", MODE_PRIVATE);
            int alignment = prefs.getInt("print_alignment", 0);
            int item = 0;
            if (alignment == 0) {
                item = 0;
            } else if (alignment == 1) {
                item = 1;
            } else if (alignment == 2) {
                item = 2;
            }
            builder.setSingleChoiceItems(alignments, item, (dialog, which) -> {
                switch (which) {
                    case 0:
                        setPrintAlignment(0);
                        break;
                    case 1:
                        setPrintAlignment(1);
                        break;
                    case 2:
                        setPrintAlignment(2);
                        break;
                }
                dialog.dismiss();
            });
            builder.show();
        });


        view.findViewById(R.id.layout_print_density).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Developing...", Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.layout_language_settings).setOnClickListener(view1 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle(R.string.select_language);

            final String[] languages = {"简体中文", "English"};
            SharedPreferences prefs = getContext().getSharedPreferences("Settings", MODE_PRIVATE);
            String language = prefs.getString("app_language", "");
            int item = 0;
            if (language != null && !language.isEmpty()) {
                if (language.equals("zh")) {
                    item = 0;
                } else if (language.equals("en")) {
                    item = 1;
                }
            }
            builder.setSingleChoiceItems(languages, item, (dialog, which) -> {
                switch (which) {
                    case 0:
                        setAppLanguage("zh");
                        break;
                    case 1:
                        setAppLanguage("en");
                        break;
                }
                dialog.dismiss();
            });
            builder.show();
        });
    }

    private void setAppLanguage(String languageCode) {
        SharedPreferences preferences = requireContext()
                .getSharedPreferences("Settings", MODE_PRIVATE);
        preferences.edit().putString("app_language", languageCode).apply();
        updateAppLanguage(languageCode);
        Intent intent = new Intent(requireActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void updateAppLanguage(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    private void setPrintAlignment(int printAlignment) {
        SharedPreferences preferences = requireContext()
                .getSharedPreferences("Settings", MODE_PRIVATE);
        preferences.edit().putInt("print_alignment", printAlignment).apply();
    }

}