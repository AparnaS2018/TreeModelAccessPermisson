package com.sampleProject.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.wicare.dataobject.PermissionTreeModel;
import com.wicare.practice.R;

import java.util.List;

/**
 * Created by aparnasalve on 09/06/17.
 */
public class AccessPermissionAdapter extends RecyclerView.Adapter<AccessPermissionAdapter.ListHeaderViewHolder> {
    public static final int HEADER = 0;
    public static final int CHILD = 1;
    List<PermissionTreeModel.Datum> permissionList;
    private List<Item> data;
    private Context mContext;
    LayoutInflater inflater;

    public AccessPermissionAdapter(Context context, List<Item> data, List<PermissionTreeModel.Datum> mPermissionList) {
        this.data = data;
        this.permissionList = mPermissionList;
        this.mContext = context;
        inflater = LayoutInflater.from(this.mContext);
    }

    @Override
    public AccessPermissionAdapter.ListHeaderViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        return new ListHeaderViewHolder(inflater.inflate(R.layout.access_permission_list_header, parent, false));
    }

    public void onBindViewHolder(final AccessPermissionAdapter.ListHeaderViewHolder holder, final int position) {
        final Item item = data.get(position);

        holder.refferalItem = item;
        holder.header_title.setText(item.text);
        if (data.get(position).isShown()) {
            holder.btn_expand_toggle.setImageResource(R.drawable.circle_minus);
            holder.llChildContainer.setVisibility(View.VISIBLE);
        } else {
            holder.btn_expand_toggle.setImageResource(R.drawable.circle_plus);
            holder.llChildContainer.setVisibility(View.GONE);
        }
        PermissionTreeModel.Datum datum = getPermissionItem(permissionList, data.get(position));

        if (datum != null && datum.getChild().size() > 0) {
            try {
                holder.llChildContainer.removeAllViews();
            } catch (Exception e) {
                e.printStackTrace();
            }
            LayoutInflater inflater;
            for (int i = 0; i < datum.getChild().size(); i++) {
                inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = getChilds(inflater, datum.getChild().get(i));
                holder.llChildContainer.addView(view);

            }

        }


        holder.btn_expand_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.get(position).setShown(!data.get(position).isShown());
                notifyDataSetChanged();
            }
        });
    }

    private View getChilds(LayoutInflater inflater, final PermissionTreeModel.Child child) {
        //Child view items

        TextView child_title;
        ToggleButton toggle_read, toggle_write, toggle_update;

        View view = inflater.inflate(R.layout.access_permission_list_child, null);

        child_title = (TextView) view.findViewById(R.id.child_title);
        toggle_read = (ToggleButton) view.findViewById(R.id.btn_read_toggle);
        toggle_write = (ToggleButton) view.findViewById(R.id.btn_write_toggle);
        toggle_update = (ToggleButton) view.findViewById(R.id.btn_update_toggle);

        child_title.setText(child.getModuleName());

//        System.out.println("Child " + child.getModuleName());
//        System.out.println("CanRead " + child.getChild().get(0).getCanRead());
//        System.out.println("CanUpdate " + child.getChild().get(1).getCanUpdate());
//        System.out.println("CanWrite " + child.getChild().get(2).getCanWrite());
//        System.out.println("-------------------------------------------------");


        if (child.getChild().get(0).getCanRead() != null && child.getChild().get(0).getCanRead()) {
            toggle_read.setChecked(true);
        } else {
            toggle_read.setChecked(false);
        }
        if (child.getChild().get(1).getCanUpdate() != null && child.getChild().get(1).getCanUpdate()) {
            toggle_update.setChecked(true);
        } else {
            toggle_update.setChecked(false);
        }
        if (child.getChild().get(2).getCanWrite() != null && child.getChild().get(2).getCanWrite()) {
            toggle_write.setChecked(true);
        } else {
            toggle_write.setChecked(false);
        }

        toggle_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                child.getChild().get(0).setCanRead(!child.getChild().get(0).getCanRead());
                notifyDataSetChanged();
            }
        });
        toggle_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                child.getChild().get(1).setCanUpdate(!child.getChild().get(1).getCanUpdate());
                notifyDataSetChanged();
            }
        });
        toggle_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                child.getChild().get(2).setCanWrite(!child.getChild().get(2).getCanWrite());
                notifyDataSetChanged();
            }
        });

        return view;
    }

    private PermissionTreeModel.Datum getPermissionItem(List<PermissionTreeModel.Datum> permissionList, Item item) {

        for (int i = 0; i < permissionList.size(); i++) {
            if (permissionList.get(i).getModuleName().equalsIgnoreCase(item.text)) {
                System.out.println("Module is for " + permissionList.get(i).getModuleName() + "& size of child is " + permissionList.get(i).getChild().size());
                return permissionList.get(i);
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ListHeaderViewHolder extends RecyclerView.ViewHolder {
        //header view items
        public TextView header_title;
        public ImageView btn_expand_toggle;
        LinearLayout llChildContainer;
        public Item refferalItem;


        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            //header view items
            header_title = (TextView) itemView.findViewById(R.id.header_title);
            btn_expand_toggle = (ImageView) itemView.findViewById(R.id.btn_expand_toggle);
            llChildContainer = (LinearLayout) itemView.findViewById(R.id.llChildContainer);

            //Child view items

        }
    }

    public static class Item {
        public int type;
        public String text;
        public List<Item> invisibleChildren;

        public boolean isShown() {
            return isShown;
        }

        public void setShown(boolean shown) {
            isShown = shown;
        }

        private boolean isShown;

        public Item() {
        }

        public Item(int type, String text) {
            this.type = type;
            this.text = text;
        }
    }
}

