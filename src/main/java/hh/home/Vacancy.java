package hh.home;

public class Vacancy {

    String nameOfVacancy;
    String sizeOfSalary;
    String nameOfOrganization;
    String locationCity;
    String responsibility;
    String requirement;
    String dateOfVacancy;

    public Vacancy (String [] paramOfVacancy ) {
        nameOfVacancy = paramOfVacancy[0];
        sizeOfSalary = paramOfVacancy[1];
        nameOfOrganization = paramOfVacancy[2];
        locationCity = paramOfVacancy[3];
        responsibility = paramOfVacancy[4];
        requirement = paramOfVacancy[5];
        dateOfVacancy = paramOfVacancy[6];
    }
}
