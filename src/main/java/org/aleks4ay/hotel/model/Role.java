package org.aleks4ay.hotel.model;

public enum Role {
    ROLE_GUEST,
    ROLE_USER,
    ROLE_MANAGER,
    ROLE_ADMIN;

    /*
        ROLE_GUEST("ROLE_GUEST"),
        ROLE_USER("ROLE_USER"),
        ROLE_MANAGER("ROLE_MANAGER"),
        ROLE_ADMIN("ROLE_ADMIN");

        private String title;

        Role(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public String toString() {
            return title;
        }*/
    public String getTitle() {
        return this.toString().replace("ROLE_", "");
    }
}
