package ir.popittv.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.databinding.ItemChannelProfileBinding;
import ir.popittv.myapplication.models.ChannelDataModel;
import ir.popittv.myapplication.utils.OnClickFrg1;

public class RvChannel_Frg1 extends RecyclerView.Adapter<RvChannel_Frg1.rvChannel_ViewHolder> {



    List<ChannelDataModel> channelDataModels;
    OnClickFrg1 onClickFrg1;
    Context context;

    public RvChannel_Frg1(FragmentActivity context , OnClickFrg1 onClickFrg1) {
        this.context = context;
        this.onClickFrg1=onClickFrg1;
    }


    @NonNull
    @Override
    public rvChannel_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

      //  View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_frg1_rv1,parent,false);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemChannelProfileBinding binding= DataBindingUtil.inflate(inflater,R.layout.item_channel_profile,parent,false);

        return new rvChannel_ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull rvChannel_ViewHolder holder, int position) {


        Glide.with(context).load(channelDataModels.get(position).getProfile_chann())
                .into(holder.binding.itemChannProfile);
        holder.binding.tvAgeItemProfileChan.setText(channelDataModels.get(position).getAge());
        holder.binding.parentItemProfileChan.setOnClickListener(v->{

            onClickFrg1.OnclickDetail(channelDataModels.get(position).getId_channel());


        });

    }

    @Override
    public int getItemCount() {
        if (channelDataModels!=null) {
            return channelDataModels.size();
        } else
            return 0;

    }

    public class rvChannel_ViewHolder extends RecyclerView.ViewHolder{

        private ItemChannelProfileBinding binding;
      //  ImageView iv_poster;
       // TextView tv_title;


        public rvChannel_ViewHolder(@NonNull ItemChannelProfileBinding binding) {
            super(binding.getRoot());

            this.binding=binding;
           // iv_poster=itemView.findViewById(R.id.ivPoster_itemFrg1_rv1);
           // tv_title=itemView.findViewById(R.id.tvTitle_itemFrg1_rv1);



        }
    }

    public void setData(List<ChannelDataModel> movieModelList){
    this.channelDataModels =movieModelList;
    notifyDataSetChanged();
    }


}
