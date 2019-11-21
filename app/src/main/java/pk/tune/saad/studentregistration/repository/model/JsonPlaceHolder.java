package pk.tune.saad.studentregistration.repository.model;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.databinding.BindingAdapter;

public class JsonPlaceHolder {

    @SerializedName("albumId")
    @Expose
    private int albumId;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("thumbnailUrl")
    @Expose
    private String thumbnailUrl;

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbnailUrl(){
        return thumbnailUrl;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @BindingAdapter("thumbnailUrl")
    public static void loadImage(ImageView view, String thumbnailUrl) {
        Glide.with(view.getContext()).load(thumbnailUrl).apply(new RequestOptions().centerCrop()).into(view);
    }
}
