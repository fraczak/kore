package com.example.kore.ui;

import com.example.kore.R;
import com.example.kore.codes.Code;
import com.example.kore.codes.Label;
import com.example.unsuck.Null;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Field extends Fragment {
  public static final String ARG_LABEL = "label";
  public static final String ARG_CODE = "code";
  public static final String ARG_SELECTED = "selected";

  public static interface CodeSelectedListener {
    public void codeSelected(Label l, Code c);
  }

  public static interface LabelSelectedListener {
    public void labelSelected(Label l);
  }

  private CodeSelectedListener codeSelectedListener;
  private LabelSelectedListener labelSelectedListener;

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    codeSelectedListener = (CodeSelectedListener) activity;
    labelSelectedListener = (LabelSelectedListener) activity;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.field, container, false);

    Bundle args = getArguments();
    final Label label = (Label) args.get(ARG_LABEL);
    final Code code = (Code) args.get(ARG_CODE);
    final Boolean selected = args.getBoolean(ARG_SELECTED);
    Null.notNull(label);
    Null.notNull(code);

    v.setOnDragListener(new OnDragListener() {
      @Override
      public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
        case DragEvent.ACTION_DRAG_ENDED:
        }
        return false;
      }
    });
    Button lb = (Button) v.findViewById(R.id.label);
    if (selected)
      lb.setText("---");
    lb.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        labelSelectedListener.labelSelected(label);
      }
    });

    ((Button) v.findViewById(R.id.code))
        .setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            codeSelectedListener.codeSelected(label, code);
          }
        });
    int c = (int) Long.parseLong(label.label.substring(0, 8), 16);
    ((TextView) v.findViewById(R.id.label)).setBackgroundColor(c);
    String cs = code.toString();
    ((TextView) v.findViewById(R.id.code)).setText(cs.substring(0,
        Math.min(10, cs.length())));
    return v;
  }
}
