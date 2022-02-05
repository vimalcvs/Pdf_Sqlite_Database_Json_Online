package com.example.jsonexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jsonexample.helper.SharedPref;
import com.example.jsonexample.helper.Tools;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;

public class PdfViewActivity extends AppCompatActivity implements OnLoadCompleteListener, OnPageErrorListener {

    ProgressBar pdfViewProgressBar;
    TextView toolbar_title;
    PDFView pdfView;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.getTheme(this);
        setContentView(R.layout.activity_pdf);
        sharedPref = new SharedPref(this);

        ImageView iv_close = findViewById(R.id.iv_close);
        iv_close.setOnClickListener(v -> PdfViewActivity.super.onBackPressed());

        pdfView = findViewById(R.id.pdfView);
        toolbar_title = findViewById(R.id.toolbar_title);

        pdfViewProgressBar = findViewById(R.id.pdfViewProgressBar);
        pdfViewProgressBar.setVisibility(View.VISIBLE);

        Intent i = this.getIntent();
        final String title = i.getExtras().getString("title");
        toolbar_title.setText(title);

        PdfView();
    }

    private void PdfView() {

        Intent i = this.getIntent();
        final String path = i.getExtras().getString("pdfURL");

        FileLoader.with(this)
                .load(path, false)
                .fromDirectory("My_PDFs", FileLoader.DIR_INTERNAL)
                .asFile(new FileRequestListener<File>() {
                    @Override
                    public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                        pdfViewProgressBar.setVisibility(View.GONE);
                        File pdfFile = response.getBody();
                        try {
                            pdfView.fromFile(pdfFile)
                                    .defaultPage(0)
                                    .enableAnnotationRendering(true)
                                    .onLoad(PdfViewActivity.this)
                                    .scrollHandle(new DefaultScrollHandle(PdfViewActivity.this))
                                    .spacing(10) // in dp
                                    .onPageError(PdfViewActivity.this)
                                    .load();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(FileLoadRequest request, Throwable t) {
                        pdfViewProgressBar.setVisibility(View.GONE);
                        Toast.makeText(PdfViewActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void loadComplete(int nbPages) {
        pdfViewProgressBar.setVisibility(View.GONE);
        Toast.makeText(PdfViewActivity.this, String.valueOf(nbPages), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPageError(int page, Throwable t) {
        pdfViewProgressBar.setVisibility(View.GONE);
        Toast.makeText(PdfViewActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

    }

}