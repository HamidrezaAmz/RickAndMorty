package ir.vasl.navigationcomponentimpl.view.locations.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.vasl.navigationcomponentimpl.R;
import ir.vasl.navigationcomponentimpl.databinding.LocationListItemBinding;
import ir.vasl.navigationcomponentimpl.models.LocationModel;
import ir.vasl.navigationcomponentimpl.utils.BaseClasses.BaseViewHolder;
import ir.vasl.navigationcomponentimpl.view.callbacks.GlobalListCallback;

public class LocationsListAdapter extends RecyclerView.Adapter<BaseViewHolder> implements GlobalListCallback {

    private List<? extends LocationModel.Result> mResults;

    private GlobalListCallback globalListCallback;

    public LocationsListAdapter(GlobalListCallback globalListCallback) {
        this.globalListCallback = globalListCallback;
    }

    public void setCharacters(List<? extends LocationModel.Result> results) {
        if (mResults == null) {
            this.mResults = results;
            notifyItemRangeInserted(0, results.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mResults.size();
                }

                @Override
                public int getNewListSize() {
                    return results.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mResults.get(oldItemPosition).getId() == results.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    LocationModel.Result newResult = results.get(newItemPosition);
                    LocationModel.Result oldResult = mResults.get(oldItemPosition);
                    return newResult.getId() == oldResult.getId()
                            && TextUtils.equals(newResult.getName(), oldResult.getName())
                            && TextUtils.equals(newResult.getType(), oldResult.getType());
                }
            });

            mResults = results;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LocationListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.location_list_item, parent, false);
        return new LocationsListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
        ((LocationsListAdapter.ViewHolder) holder).binding.setResult(mResults.get(position));
        ((LocationsListAdapter.ViewHolder) holder).binding.setGlobalListCallback(this);
    }

    @Override
    public int getItemCount() {
        return mResults == null ? 0 : mResults.size();
    }

    @Override
    public void onItemClicked(Object object) {
        if (globalListCallback != null)
            globalListCallback.onItemClicked(object);
    }

    static class ViewHolder extends BaseViewHolder {

        private LocationListItemBinding binding;

        public ViewHolder(@NonNull LocationListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
