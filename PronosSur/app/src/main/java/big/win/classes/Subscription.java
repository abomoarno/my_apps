package big.win.classes;

public class Subscription {

    private String title;
    private String description;
    private String amount;
    private String amount_usd;
    private String amount_cfa;
    private String currency;
    private long time;

    public Subscription(String title, String description, String amount, String currency,
                        String amount_usd, String amount_cfa, long time) {
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.currency = currency;
        this.amount_usd = amount_usd;
        this.amount_cfa = amount_cfa;
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount_cfa() {
        return amount_cfa;
    }

    public void setAmount_cfa(String amount_cfa) {
        this.amount_cfa = amount_cfa;
    }

    public String getAmount_usd() {
        return amount_usd;
    }

    public void setAmount_usd(String amount_usd) {
        this.amount_usd = amount_usd;
    }
}
