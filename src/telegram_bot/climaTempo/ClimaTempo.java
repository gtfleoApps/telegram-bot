package telegram_bot.climaTempo;

public class ClimaTempo {
    private Long id;
    private String name;
    private String state;
    private String country;
    /*
     * HTTP/1.1 200 OK
     * {
     * id: 3477,
     * name: "São Paulo",
     * state: "SP",
     * country: "BR",
     * data: {
     * temperature: 23.8,
     * wind_direction: "NW",
     * wind_velocity: 22,
     * humidity: 43,
     * condition: "Poucas nuvens",
     * pressure: 1008,
     * icon: "2",
     * sensation: 27,
     * date: "2017-10-01 12:37:00"
     * }
     * }
     */

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return "Tempo{" + " id=" + id + ","
                + " nome=" + name + ","
                + " estado=" + state + ","
                + " país=" + country + "}";
    }
}
