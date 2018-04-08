package bankzworld.com.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bankzworld.com.R;
import bankzworld.com.data.Medication;
import bankzworld.com.fragment.MainActivityFragment;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.ViewHolder> {

    private static final String TAG = "MedicationAdapter";

    private List<Medication> medicationList;
    private int lastPosition = -1;

    // constructor
    public MedicationAdapter(List<Medication> medicationList) {
        this.medicationList = medicationList;
    }

    // inflates layout
    @NonNull
    @Override
    public MedicationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medication_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds views
    @Override
    public void onBindViewHolder(@NonNull final MedicationAdapter.ViewHolder holder, final int position) {

        Context context = holder.itemView.getContext();

        holder.mName.setText(medicationList.get(position).getName());
        holder.mDesc.setText(medicationList.get(position).getDescription());

        // retrieve the id from the room database
        int id = medicationList.get(position).getId();

        // set the tag of itemView in the holder to the id
        holder.itemView.setTag(id);

        // sets an animation for the recyclerView
        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.up
                        : R.anim.down);
        holder.itemView.startAnimation(animation);
        lastPosition = position;

    }

    // returns views
    @Override
    public int getItemCount() {
        if (medicationList == null)
            return 0;
        return medicationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mName, mDesc;

        public ViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.name);
            mDesc = (TextView) itemView.findViewById(R.id.desc);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            getAlertDialog(context);
        }

        public void getAlertDialog(Context context) {
            Medication medication = medicationList.get(getLayoutPosition());

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Drug Info");
            builder.setCancelable(false);
            builder.setMessage(medication.getName() + " is a "
                    + medication.getDescription()
                    + " drug and should be taken "
                    + medication.getNumOfDoze() + "/" + medication.getNumOfTimes() +
                    " times a day" + "\n" + " This drug is expected to last for "
                    + medication.getEndDate() + " days/");

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}
