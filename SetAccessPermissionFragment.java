package com.sampleProject.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wicare.adapters.AccessPermissionAdapter;
import com.wicare.dataobject.AppInstance;
import com.wicare.dataobject.PermissionTreeModel;
import com.wicare.dataobject.saveAccessPermissionRole;
import com.wicare.interfaces.ServiceRedirection;
import com.wicare.managers.PracticeDeskManager;
import com.wicare.practice.R;
import com.wicare.utils.Constants;
import com.wicare.utils.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aparnasalve on 11/9/17.
 */

public class SetAccessPermissionFragment extends Fragment implements View.OnClickListener, ServiceRedirection {


    private String appointmentId;
    private String result;
    CharSequence[] providerList;
    private Utility mUtility;
    private Context mContext;

    private View mRootView;
    ImageView mBackBtn;
    TextView mHeaderTextView, errorMessage, rightTextView;
    private String roleName = "", roleID = "", roleDesc = "";
    private RecyclerView accessPermissionRecyclerView;

    private PracticeDeskManager mPracticeDeskManager;
    List<AccessPermissionAdapter.Item> data = new ArrayList<>();
    private Button btnSaveAccess;
    private List<PermissionTreeModel.Datum> permissionList;

    private PermissionTreeModel samplePermissionModel;

    private saveAccessPermissionRole saveAccessPermissionRole;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_set_access_permission, container, false);
        mUtility = new Utility(getActivity());
        mContext = getActivity();
        initComponents();
        setHeaderView();
        findViews();
        return mRootView;
    }


    private void initComponents() {
        if (getArguments() != null) {
            roleName = getArguments().getString("roleName");
            roleID = getArguments().getString("roleID");
            roleDesc = getArguments().getString("roleDesc");
        }
        samplePermissionModel=new PermissionTreeModel();
        saveAccessPermissionRole=new saveAccessPermissionRole();
        btnSaveAccess=(Button)mRootView.findViewById(R.id.btnSaveAccess);
        btnSaveAccess.setOnClickListener(this);
        mPracticeDeskManager = new PracticeDeskManager(mContext, this);

        accessPermissionRecyclerView = (RecyclerView) mRootView.findViewById(R.id.accessPermissionRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        accessPermissionRecyclerView.setLayoutManager(layoutManager);
    }

    private void findViews() {
        mPracticeDeskManager = new PracticeDeskManager(mContext, this);
        if (mUtility.isNetworkAvailable()) {
            mUtility.startLoader(mContext, R.drawable.image_for_rotation);
            mPracticeDeskManager.getPermissionTree(roleID);
        }
    }

    private void setHeaderView() {
        View myHeaderLayout = mRootView.findViewById(R.id.toolbar);
        mBackBtn = (ImageView) myHeaderLayout.findViewById(R.id.backButton);
        mBackBtn.setOnClickListener(this);
        mHeaderTextView = (TextView) myHeaderLayout.findViewById(R.id.header);
        mHeaderTextView.setText("" + roleDesc+" Access Permission");
        rightTextView = (TextView) myHeaderLayout.findViewById(R.id.rightTextView);
        rightTextView.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backButton:
                closeFragment();
                break;
            case R.id.btnSaveAccess:
                updateAccessPermission();
                break;
        }

    }


    private void updateAccessPermission(){
        int dataSize=samplePermissionModel.getData().size();
        int dataMerge=dataSize;

        saveAccessPermissionRole.permissions = saveAccessPermissionRole.new SavePermissions();

        saveAccessPermissionRole.permissions.data=samplePermissionModel.getData();
        saveAccessPermissionRole.role_id=roleID;
        if (mUtility.isNetworkAvailable()) {

            mUtility.startLoader(mContext, R.drawable.image_for_rotation);
            mPracticeDeskManager.updateAccessPermissionModel(saveAccessPermissionRole);

        }

            else{
                mUtility.showCustomAlertDialog(mContext, getString(R.string.app_name), "Please add Alpha key for Practice.", null, null, false, null);
            }


    }

    private void closeFragment() {
        PracticeDeskFragment clinicalDeskFragment = new PracticeDeskFragment();
        FragmentTransaction ftPractice = getActivity().getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("FROMFRAGMENT", "access_rights");
        clinicalDeskFragment.setArguments(bundle);
        ftPractice.replace(R.id.parentFrame, clinicalDeskFragment);
        ftPractice.commit();
    }


    @Override
    public void onSuccessRedirection(int taskID) {
        mUtility.stopLoader();
        if (taskID == Constants.TaskID.PERMISSION_TREE_TASK_ID) {

            samplePermissionModel=AppInstance.permissionTreeModel;
           permissionList = samplePermissionModel.getData();
            if (permissionList != null && permissionList.size() > 0) {
                for (int p = 0; p < permissionList.size(); p++) {
                    PermissionTreeModel.Datum model = permissionList.get(p);
                    AccessPermissionAdapter.Item parent = new AccessPermissionAdapter.Item(AccessPermissionAdapter.HEADER, model.getModuleName());
                    parent.invisibleChildren = new ArrayList<>();
                    for (int c = 0; c < model.getChild().size(); c++) {
                        parent.invisibleChildren.add(new AccessPermissionAdapter.Item(AccessPermissionAdapter.CHILD, model.getChild().get(c).getModuleName()));
                    }
                    data.add(parent);
                }
                accessPermissionRecyclerView.setAdapter(new AccessPermissionAdapter(getActivity(), data, permissionList));
            }
        }


        if (taskID == Constants.TaskID.SAVE_ROLE_PERMISSION) {

           closeFragment();
        }




    }

    @Override
    public void onFailureRedirection(String errorMessage) {
        mUtility.stopLoader();
    }
}
