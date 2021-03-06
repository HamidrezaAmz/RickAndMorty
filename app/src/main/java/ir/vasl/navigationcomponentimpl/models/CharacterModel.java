package ir.vasl.navigationcomponentimpl.models;

import androidx.databinding.BindingAdapter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import ir.vasl.navigationcomponentimpl.view.customViews.MagicalImageView;

public class CharacterModel implements Serializable {

    public Info info;
    public List<Result> results;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public static class Info implements Serializable {

        public int count;
        public int pages;
        public String next;
        public String prev;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public String getPrev() {
            return prev;
        }

        public void setPrev(String prev) {
            this.prev = prev;
        }
    }

    public static class Origin implements Serializable {
        public String name;
        public String url;

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
    }

    public static class Location implements Serializable {
        public String name;
        public String url;

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
    }

    public static class Result implements Serializable {

        public int id;
        public String name;
        public String status;
        public String species;
        public String type;
        public String gender;
        public Origin origin;
        public Location location;
        public String image;
        public List<String> episode;
        public String url;
        public Date created;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSpecies() {
            return species;
        }

        public void setSpecies(String species) {
            this.species = species;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Origin getOrigin() {
            return origin;
        }

        public void setOrigin(Origin origin) {
            this.origin = origin;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public List<String> getEpisode() {
            return episode;
        }

        public void setEpisode(List<String> episode) {
            this.episode = episode;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Date getCreated() {
            return created;
        }

        public void setCreated(Date created) {
            this.created = created;
        }

        @BindingAdapter("avatarImage")
        public static void loadImage(MagicalImageView view, String imageUrl) {
            view.setImageUrl(imageUrl);
        }
    }

}
