package com.example.meet.kissankart;

import android.widget.ImageButton;

/**
 * Created by Meet on 19-12-2017.
 */

public class buyercategory {

        private int id;
        private String Title,Image;

        public buyercategory(int id, String title, String image)
        {
            this.id = id;
            Title = title;
            Image = image;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getImage() {
            return Image;
        }

        public void setImage(String image) {
            Image = image;
        }
    }

