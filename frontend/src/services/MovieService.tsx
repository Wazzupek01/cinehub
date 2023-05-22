class MovieService {
  private MainPageTitles: string[] = [
    "Mad Max: Fury Road",
    "The SpongeBob Movie: Sponge Out of Water",
    "Inception",
    "Iron Man 2",
    "Fear and Loathing in Las Vegas"
  ];

  private async getCarouselMovie(title: string) {
    const res = await fetch(
      "http://localhost:8080/movies/title/" + title + "/0"
    );
    const data = await res.text();
    return JSON.parse(data).content[0];
  }

  async loadCarouselMovies() {
    let carouselMovies: Object[] = [];
    for (let i = 0; i < this.MainPageTitles.length; i++) {
      let movie = await this.getCarouselMovie(this.MainPageTitles[i]);
      carouselMovies.push(movie);
    }
    return carouselMovies;
  }

  async getMovieById(id: string){
    const response = await fetch("http://localhost:8080/movies/id/" + id);
    const data = await response.text();
    return data;
  }

  async getAllMoviesSorted(page: Number, sortBy: string, isAscending: boolean) {
    const response = await fetch(
      "http://localhost:8080/movies/all/" +
        page +
        "/" +
        sortBy +
        "/" +
        isAscending
    );
    const data = await response.text();
    return data;
  }

  async getMoviesByDirectorSorted(page: Number, orderBy: string, filterValue: string, isAscending: boolean){
    const response = await fetch(
      "http://localhost:8080/movies/director/" +
        filterValue +
        "/" +
        page +
        "/" +
        orderBy +
        "/"+
        isAscending
    );

    const data = await response.text();
    return data;
  }

  async getMoviesByRuntimeSorted(page: Number, orderBy: string, filterValue: string, isAscending: boolean){
    const values = filterValue.split('-');

    const response = await fetch(
      "http://localhost:8080/movies/runtime/" +
        values[0] +
        "/" +
        values[1] +
        "/" +
        page +
        "/" +
        orderBy +
        "/"+
        isAscending
    );

    const data = await response.text();
    return data;
  }

  async getMoviesByGenreSorted(page: Number, orderBy: string, filterValue: string, isAscending: boolean){
    const response = await fetch(
      "http://localhost:8080/movies/genre/" +
        filterValue +
        "/" +
        page +
        "/" +
        orderBy +
        "/"+
        isAscending
    );

    const data = await response.text();
    return data;
  }

  async getMoviesByTitle(page: Number, name: String) {
    const response = await fetch(
      "http://localhost:8080/movies/title/" + name + "/" + page
    );
    const data = await response.text();
    return data;
  }
}

export default MovieService;
