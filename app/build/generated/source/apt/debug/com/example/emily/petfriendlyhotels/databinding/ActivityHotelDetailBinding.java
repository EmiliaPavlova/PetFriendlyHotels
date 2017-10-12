package com.example.emily.petfriendlyhotels.databinding;
import com.example.emily.petfriendlyhotels.R;
import com.example.emily.petfriendlyhotels.BR;
import android.view.View;
public class ActivityHotelDetailBinding extends android.databinding.ViewDataBinding  {

    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    public final android.widget.ImageView imgCovers;
    private final android.widget.ScrollView mboundView0;
    public final android.widget.TextView tvAddress;
    public final android.widget.TextView tvRating;
    public final android.widget.TextView tvTitle;
    // variables
    private com.example.emily.petfriendlyhotels.Hotel mHotel;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityHotelDetailBinding(android.databinding.DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds);
        this.imgCovers = (android.widget.ImageView) bindings[1];
        this.imgCovers.setTag(null);
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
        this.tvAddress = (android.widget.TextView) bindings[3];
        this.tvAddress.setTag(null);
        this.tvRating = (android.widget.TextView) bindings[4];
        this.tvRating.setTag(null);
        this.tvTitle = (android.widget.TextView) bindings[2];
        this.tvTitle.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    public boolean setVariable(int variableId, Object variable) {
        switch(variableId) {
            case BR.Hotel :
                setHotel((com.example.emily.petfriendlyhotels.Hotel) variable);
                return true;
        }
        return false;
    }

    public void setHotel(com.example.emily.petfriendlyhotels.Hotel Hotel) {
        this.mHotel = Hotel;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.Hotel);
        super.requestRebind();
    }
    public com.example.emily.petfriendlyhotels.Hotel getHotel() {
        return mHotel;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        java.lang.String hotelRating = null;
        java.lang.String hotelAddress = null;
        java.lang.String hotelImageUrl = null;
        java.lang.String hotelName = null;
        com.example.emily.petfriendlyhotels.Hotel hotel = mHotel;

        if ((dirtyFlags & 0x3L) != 0) {



                if (hotel != null) {
                    // read Hotel.rating
                    hotelRating = hotel.rating;
                    // read Hotel.address
                    hotelAddress = hotel.address;
                    // read Hotel.imageUrl
                    hotelImageUrl = hotel.imageUrl;
                    // read Hotel.name
                    hotelName = hotel.name;
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            com.example.emily.petfriendlyhotels.Hotel.loadImage(this.imgCovers, hotelImageUrl);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.tvAddress, hotelAddress);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.tvRating, hotelRating);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.tvTitle, hotelName);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;

    public static ActivityHotelDetailBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityHotelDetailBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot, android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityHotelDetailBinding>inflate(inflater, com.example.emily.petfriendlyhotels.R.layout.activity_hotel_detail, root, attachToRoot, bindingComponent);
    }
    public static ActivityHotelDetailBinding inflate(android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityHotelDetailBinding inflate(android.view.LayoutInflater inflater, android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.example.emily.petfriendlyhotels.R.layout.activity_hotel_detail, null, false), bindingComponent);
    }
    public static ActivityHotelDetailBinding bind(android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityHotelDetailBinding bind(android.view.View view, android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_hotel_detail_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityHotelDetailBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): Hotel
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}