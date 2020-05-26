package com.example.aplikacja;

import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class HistoryDataFragment extends Fragment {

    private static final String TAG = "HistoryDataFragment";

    Button readButton;
    TextView historyTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.historydata, container, false);

        historyTextView =  view.findViewById(R.id.HistoryTextView);

        readButton = view.findViewById(R.id.ReadButton);

        readButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view){
                ReadData();
            }
        });

        return view;
    }

    public void ReadData()
    {
        checkFolder();

        File folder = getContext().getFilesDir();
        File f = new File(folder, "DataMeasure");

        StringBuffer sb = new StringBuffer();

        try{
            File[] files = f.listFiles();

            if (files.length == 0){
                Toast.makeText(getContext().getApplicationContext(), "No data to read!", Toast.LENGTH_SHORT).show();
            }
            else{

                for (int i = 0; i < files.length; i++)
                {
                    Scanner scanner = new Scanner(new File(f, files[i].getName()));

                    sb.append("Log from: " + files[i].getName() + "\n\n");

                    while(scanner.hasNextLine())
                    {
                        sb.append(scanner.nextLine() + "\n");
                    }

                    sb.append("\n\n");

                    scanner.close();
                }

                historyTextView.setText(sb.toString());
                Toast.makeText(getContext().getApplicationContext(), "Data loaded!", Toast.LENGTH_SHORT).show();
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void checkFolder() {
        File folder = getContext().getFilesDir();
        File f= new File(folder, "DataMeasure");

        boolean isDirectoryCreated = f.exists();
        if (!isDirectoryCreated) {
            isDirectoryCreated = f.mkdir();
        }
    }


}
