package voiq.com.appvoiq.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 Clase que contendrá la información detallada del pokemón seleccionado.
 */
public class ObjectPokemon implements Parcelable {

    private String name;
    private String url;
    private String nationalId;
    private String sex;

    private int count;
    private String next;
    private String listaPokemon;


    public ObjectPokemon() {
    }

    public ObjectPokemon(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public ObjectPokemon(String name, String url, String nationalId, String sex) {
        this.name = name;
        this.url = url;
        this.nationalId = nationalId;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeString(this.nationalId);
        dest.writeString(this.sex);
    }

    protected ObjectPokemon(Parcel in) {
        this.name = in.readString();
        this.url = in.readString();
        this.nationalId = in.readString();
        this.sex = in.readString();
    }

    public static final Creator<ObjectPokemon> CREATOR = new Creator<ObjectPokemon>() {
        public ObjectPokemon createFromParcel(Parcel source) {
            return new ObjectPokemon(source);
        }

        public ObjectPokemon[] newArray(int size) {
            return new ObjectPokemon[size];
        }
    };
}
