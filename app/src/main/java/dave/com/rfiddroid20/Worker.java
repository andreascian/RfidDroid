package dave.com.rfiddroid20;
import java.util.Date;
import java.util.Objects;

/**
 * Created by david on 10/01/2017.
 */

public class Worker{
    //public static long WORKING_HOUR = 28800000;  //8 hours
    public static long WORKING_HOUR = 10000;   //10 seconds for tests
    private String code;
    private String name;
    private Date entryDate;
    private Date exitDate;
    private String causal;

    public Worker(){
        this(null,null,null,null);
    }

    public Worker(String code, String name, Date entryDate, Date exitDate) {
        this.code = code;
        this.name = name;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
    }

    public String getCausal() {
        return causal;
    }

    public void setCausal(String causal) {
        this.causal = causal;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getExitDate() {
        return exitDate;
    }

    public void setExitDate(Date exitDate) {
        this.exitDate = exitDate;
    }
}
