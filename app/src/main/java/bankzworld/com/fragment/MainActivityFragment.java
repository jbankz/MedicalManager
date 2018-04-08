package bankzworld.com.fragment;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import bankzworld.com.R;
import bankzworld.com.activity.AddMedicationActivity;
import bankzworld.com.activity.LoginActivity;
import bankzworld.com.adapter.MedicationAdapter;
import bankzworld.com.data.AppDatabase;
import bankzworld.com.data.Medication;
import bankzworld.com.util.NotificationUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivityFragment extends Fragment {

    private static final String TAG = "MainActivityFragment";

    @BindView(R.id.fab)
    public FloatingActionButton fab;
    public AppDatabase db;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    FirebaseAuth.AuthStateListener authListener;
    FirebaseAuth auth;
    private List<Medication> medicationList = new ArrayList<>();

    // mandatory constructor
    public MainActivityFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_fragment, container, false);

        setHasOptionsMenu(true);

        // initialises view
        ButterKnife.bind(this, view);

        // tests to see if user is signed in
        testForUsersAuthentication();

        // sets the recycler view
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);

        // registers the recycler view for the menu
        registerForContextMenu(mRecyclerView);

        db = Room.databaseBuilder(getContext(), AppDatabase.class, "production").build();
        // calls the background thread
        new GetAllItem().execute();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // starts another activity
                startActivity(new Intent(getContext(), AddMedicationActivity.class));

            }
        });

        // call the onSwipeToDelete Method
        onSwipeToDelete();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);

        final MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // finds out which menu item was pressed
        switch (item.getItemId()) {
            case R.id.action_jan:
                getMonth("01");
                return true;
            case R.id.action_feb:
                getMonth("02");
                return true;
            case R.id.action_mar:
                getMonth("03");
                return true;
            case R.id.action_apr:
                getMonth("04");
                return true;
            case R.id.action_may:
                getMonth("05");
                return true;
            case R.id.action_jun:
                getMonth("06");
                return true;
            case R.id.action_jul:
                getMonth("07");
                return true;
            case R.id.action_aug:
                getMonth("08");
                return true;
            case R.id.action_sep:
                // perform action
                getMonth("09");
                return true;
            case R.id.action_oct:
                // perform action
                getMonth("10");
                return true;
            case R.id.action_nov:
                // perform action
                getMonth("11");
                return true;
            case R.id.action_dec:
                // perform action
                getMonth("12");
                return true;
            default:
                return false;
        }
    }

    public void search(final SearchView searchView) {
        searchView.setQueryHint(getString(R.string.search_query));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: called");
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterQuery(medicationList, newText);
                return false;
            }
        });
    }

    // performs a search query
    public void filterQuery(List<Medication> p, String query) {
        query = query.toLowerCase();
        final List<Medication> filteredList = new ArrayList<>();
        for (Medication medication : p) {
            final String text = medication.getName().toLowerCase();
            if (text.startsWith(query)) {
                filteredList.add(medication);
            }
        }
        mRecyclerView.setAdapter(new MedicationAdapter(filteredList));
    }

    // checks if user is already logged in
    private void testForUsersAuthentication() {

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    getActivity().finish();
                }
            }
        };
    }

    // deletes an item from the list
    public void onSwipeToDelete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                // get the viewholder item tag and store it in an integer variable
                int id = viewHolder.getAdapterPosition();

                // call to execute the background thread
                executeDeleteItem(medicationList.get(id));

                // call to repopulate the view so as to avoid unnecessary space among views
                new GetAllItem().execute();
            }
        }).attachToRecyclerView(mRecyclerView);
    }

    // called to execute the background thread
    public void executeDeleteItem(Medication medication) {
        new DeleteItem().execute(medication);
    }

    public void getMonth(String item) {
        if (!item.isEmpty()) {
            execute(item);
        } else {
            Toast.makeText(getContext(), item, Toast.LENGTH_SHORT).show();
        }
    }

    public void execute(String medication) {
        new GetMonthCategory().execute(medication);
    }

    // background thread for performing get all item from room
    class GetAllItem extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            medicationList = db.medicationDao().getAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mRecyclerView.setAdapter(new MedicationAdapter(medicationList));
        }
    }

    // background thread for performing a delete request from the room
    class DeleteItem extends AsyncTask<Medication, Void, Void> {
        @Override
        protected Void doInBackground(Medication... medications) {
            db.medicationDao().deleteListItem(medications[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // disables alarm in case there is an alarm set or not
            NotificationUtil.setAlarm(getContext(), "");
        }
    }

    // background thread for performing a request of a single item from the room
    class GetMonthCategory extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            medicationList = db.medicationDao().getSingleItem(strings[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void medication) {
            super.onPostExecute(medication);
            Log.d(TAG, "onPostExecute: " + medicationList);
            mRecyclerView.setAdapter(new MedicationAdapter(medicationList));
        }
    }
}
