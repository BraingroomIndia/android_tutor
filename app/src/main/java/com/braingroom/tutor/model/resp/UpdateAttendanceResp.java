package com.braingroom.tutor.model.resp;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateAttendanceResp extends BaseResp{

    @SerializedName("braingroom")
    @Expose
    private List<Snippet> data = null;

    @Override
    public boolean getResCode() {
        return !data.isEmpty();
    }

    public List<Snippet> getSnippet() {
        return data;
    }

    public class Snippet {

        @SerializedName("Tickets")
        @Expose
        private Tickets tickets;

        public Tickets getTickets() {
            return tickets;
        }

    }
    public class Tickets {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("vendor_classe_id")
        @Expose
        private String vendorClasseId;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("ticket_id")
        @Expose
        private String ticketId;
        @SerializedName("start_code")
        @Expose
        private String startCode;
        @SerializedName("end_code")
        @Expose
        private String endCode;
        @SerializedName("start_status")
        @Expose
        private String startStatus;
        @SerializedName("end_status")
        @Expose
        private String endStatus;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVendorClasseId() {
            return vendorClasseId;
        }

        public String getUserId() {
            return userId;
        }

        public String getTicketId() {
            return ticketId;
        }

        public String getStartCode() {
            return startCode;
        }

        public String getEndCode() {
            return endCode;
        }

        public String getStartStatus() {
            return startStatus;
        }

        public String getEndStatus() {
            return endStatus;
        }

    }

}