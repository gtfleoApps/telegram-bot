package telegram_bot.climaTempo;

public class LocalidadeDeClimaTempo {
    private String country;
    private String name;
    private String state;
    private String province;

    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public String getProvince() {
        return province;
    }

    @Override
    public String toString() {
        return "Localidade{" + "país=" + country
                + "nome=" + name
                + "estado=" + state
                + "província=" + province
                + '}';
    }
}
