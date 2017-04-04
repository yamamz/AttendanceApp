package com.yamamz.attendanceapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputType;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.yamamz.attendanceapp.adapters.ClassAdapter;
import com.yamamz.attendanceapp.models.Class_name;
import com.yamamz.attendanceapp.models.DataHelper;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class ActivityAddClass extends AppCompatActivity {

    private Realm realm;
    private CharSequence filename;
    private ClassAdapter mAdapter;
    private RecyclerView recyclerView;
    private List<Class_name> class_names = new ArrayList<>();
    private  FloatingActionButton fab;
    private LinearLayout emptyTextView;

    private class TouchHelperCallback extends ItemTouchHelper.SimpleCallback {


        TouchHelperCallback() {

            super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);

        }


        @Override

        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

            return true;

        }


        @Override

        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {

            final CoordinatorLayout coordinatorLayout=(CoordinatorLayout) findViewById(R.id.main_content);
            final Snackbar snackbar=Snackbar.make(coordinatorLayout,"Delete the Item?",Snackbar
                    .LENGTH_LONG).setDuration(2000).setAction("Yes", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DataHelper.deleteItemClassAsync(realm, viewHolder.getItemId());
                }

            });
            snackbar.show();

snackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
    @Override
    public void onDismissed(Snackbar transientBottomBar, int event) {
        super.onDismissed(transientBottomBar, event);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }

});



        }


        @Override

        public boolean isLongPressDragEnabled() {

            return true;

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        emptyTextView=(LinearLayout) findViewById(R.id.emptyView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupWindowAnimations();
        }

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        setupRecyclerView();
 fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new MaterialDialog.Builder(ActivityAddClass.this).title(R.string.input).inputType
                        (InputType.TYPE_CLASS_TEXT).input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                filename = input;

                                if (!filename.toString().equals("")) {
                                    addClass();

                                }



                            }

                        }






                ).show();

            }
        });

        loadlocationsDatabase();

    }

    private void setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            // Re-enter transition is executed when returning to this activity
            Slide slideTransition = new Slide();
        slideTransition.setSlideEdge(Gravity.LEFT);
        slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        getWindow().setReenterTransition(slideTransition);
        getWindow().setExitTransition(slideTransition);
    }
    }


    private void setupRecyclerView() {

        mAdapter = new ClassAdapter(this, realm.where(Class_name.class).findAll());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.enableDeletionMode(true);
        TouchHelperCallback touchHelperCallback = new TouchHelperCallback();
        ItemTouchHelper touchHelper = new ItemTouchHelper(touchHelperCallback);
        touchHelper.attachToRecyclerView(recyclerView);
        checkAdapter();

    }
   void checkAdapter(){
       mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
           @Override
           public void onChanged() {
               super.onChanged();
               checkRecyclerViewIsemplty();
           }
           @Override
           public void onItemRangeChanged(int positionStart, int itemCount) {
               super.onItemRangeChanged(positionStart, itemCount);
               checkRecyclerViewIsemplty();
           }

           @Override
           public void onItemRangeRemoved(int positionStart, int itemCount) {
               super.onItemRangeRemoved(positionStart, itemCount);
               checkRecyclerViewIsemplty();
           }

           @Override
           public void onItemRangeInserted(int positionStart, int itemCount) {
               super.onItemRangeInserted(positionStart, itemCount);
               checkRecyclerViewIsemplty();
           }
       });



    }


    public void loadlocationsDatabase(){
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    void addClass() {

        if (filename != null) {
           DataHelper.addClass(realm,filename.toString());
            mAdapter.notifyDataSetChanged();
            

        }

    }

    public void maketransition(String className){



            ActivityOptionsCompat options=ActivityOptionsCompat.makeSceneTransitionAnimation(this);
            Intent intent=new Intent(this, Activity_add_student.class);
            intent.putExtra("className",className);
            startActivity(intent,options.toBundle());
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_settings:
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkRecyclerViewIsemplty(){

        if(mAdapter.getItemCount()==0){

            emptyTextView.setVisibility(View.VISIBLE);
        }

        else{

            emptyTextView.setVisibility(View.GONE);
        }


    }


}

