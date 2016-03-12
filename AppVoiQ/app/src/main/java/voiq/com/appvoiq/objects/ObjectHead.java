package voiq.com.appvoiq.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Andres Rubiano on 12/03/2016.
 *
 * Objeto que contendrá la información del objeto de la lista.
 */
public class ObjectHead implements Parcelable {

    private int count;

    private String next;

    private List<ObjectPokemon> pokemones;

    public ObjectHead(int count, String next, List<ObjectPokemon> pokemones) {
        this.count = count;
        this.next = next;
        this.pokemones = pokemones;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public List<ObjectPokemon> getPokemones() {
        return pokemones;
    }

    public void setPokemones(List<ObjectPokemon> pokemones) {
        this.pokemones = pokemones;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.count);
        dest.writeString(this.next);
        dest.writeTypedList(pokemones);
    }

    protected ObjectHead(Parcel in) {
        this.count = in.readInt();
        this.next = in.readString();
        this.pokemones = in.createTypedArrayList(ObjectPokemon.CREATOR);
    }

    public static final Parcelable.Creator<ObjectHead> CREATOR = new Parcelable.Creator<ObjectHead>() {
        public ObjectHead createFromParcel(Parcel source) {
            return new ObjectHead(source);
        }

        public ObjectHead[] newArray(int size) {
            return new ObjectHead[size];
        }
    };
}
