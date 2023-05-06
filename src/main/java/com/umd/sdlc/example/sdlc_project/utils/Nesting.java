package com.umd.sdlc.example.sdlc_project.utils;

import java.util.Arrays;
import java.util.List;

public class Nesting {
    
    private String email;

    public Nesting(String email) {
        this.email = email;
    }

    public String getSanitizedEmail() {
        InnerNest in = new InnerNest(this.email);
        return in.runEmailThroughConversion();
    }

    private class InnerNest {

        private String[] email;

        public InnerNest(String email) {
            this.email = email.split("");
        }

        public String runEmailThroughConversion() {
            InnerInnerNest iin = new InnerInnerNest(this.email);
            return iin.covertListToString();
        }

        private class InnerInnerNest {
            private List<String> email;

            public InnerInnerNest(String[] email) {
                this.email = Arrays.asList(email);
            }

            public String covertListToString() {
                return this.email.toString();
            }
        }
    }
}
