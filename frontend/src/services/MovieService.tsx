class MovieService {
  private MainPageTitles: string[] = [
    "Fear and Loathing in Las Vegas",
    "Inception",
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

  async getAllMoviesSorted(page: Number, sortBy: String, isAscending: boolean) {
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

  async getMoviesByTitle(page: Number, name: String) {
    const response = await fetch(
      "http://localhost:8080/movies/title/" + name + "/" + page
    );
    const data = await response.text();
    return data;
  }
}

export default MovieService;
