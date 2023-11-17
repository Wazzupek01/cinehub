import { IMovieDto } from "../interfaces/IMovieDto";

class MovieService {
  private MainPageTitles: string[] = [
    "Mad Max: Fury Road",
    "The SpongeBob Movie: Sponge Out of Water",
    "Inception",
    "Iron Man 2",
    "Fear and Loathing in Las Vegas",
  ];

  async addMovie(formData: FormData) {
    const res = await fetch("http://localhost:8080/movies/add", {
      method: "POST",
      mode: "cors",
      credentials: "include",
      redirect: "follow",
      body: formData,
    });

    const data = await res.text();
    return data;
  }

  async deleteMovieById(id: string) {
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');

    fetch("http://localhost:8080/movies/delete/" + id, {
      method: "DELETE",
      headers: headers,
      mode: "cors",
      credentials: "include",
      redirect: "follow"
    })
  }

  private async getCarouselMovie(title: string): Promise<IMovieDto> {
    const res = await fetch(
      "http://localhost:8080/movies/title/" + title + "/0"
    );
    const data = await res.text();
    return JSON.parse(data).content[0];
  }

  async loadCarouselMovies() {
    const carouselMovies: IMovieDto[] = [];
    for (let i = 0; i < this.MainPageTitles.length; i++) {
      const movie: IMovieDto = await this.getCarouselMovie(
        this.MainPageTitles[i]
      );
      carouselMovies.push(movie);
    }
    return carouselMovies;
  }

  async getMovieById(id: string): Promise<string> {
    const response = await fetch("http://localhost:8080/movies/id/" + id);
    const data = await response.text();
    return data;
  }

  async getAllMoviesSorted(
    page: number,
    orderBy: string,
    isAscending: string
  ): Promise<string> {
    const response = await fetch(
      "http://localhost:8080/movies/all/" +
        page +
        "/" +
        orderBy +
        "/" +
        isAscending
    );
    const data = await response.text();
    return data;
  }

  async getMoviesByDirectorSorted(
    page: number,
    orderBy: string,
    filterValue: string | undefined,
    isAscending: string
  ): Promise<string> {
    const response = await fetch(
      "http://localhost:8080/movies/director/" +
        filterValue +
        "/" +
        page +
        "/" +
        orderBy +
        "/" +
        isAscending
    );

    const data = await response.text();
    return data;
  }

  async getMoviesByActorSorted(
    page: number,
    orderBy: string,
    filterValue: string | undefined,
    isAscending: string
  ): Promise<string> {
    const response = await fetch(
      "http://localhost:8080/movies/actor/" +
        filterValue +
        "/" +
        page +
        "/" +
        orderBy +
        "/" +
        isAscending
    );

    const data = await response.text();
    return data;
  }

  async getMoviesByRuntimeSorted(
    page: number,
    orderBy: string,
    filterValue: string,
    isAscending: string
  ): Promise<string> {
    const values = filterValue.split("-");

    const response = await fetch(
      "http://localhost:8080/movies/runtime/" +
        values[0] +
        "/" +
        values[1] +
        "/" +
        page +
        "/" +
        orderBy +
        "/" +
        isAscending
    );

    const data = await response.text();
    return data;
  }

  async getMoviesByGenreSorted(
    page: number,
    orderBy: string,
    filterValue: string | undefined,
    isAscending: string
  ): Promise<string> {
    const response = await fetch(
      "http://localhost:8080/movies/genre/" +
        filterValue +
        "/" +
        page +
        "/" +
        orderBy +
        "/" +
        isAscending
    );

    const data = await response.text();
    return data;
  }

  async getMoviesByTitle(page: number, name: string): Promise<string> {
    const response = await fetch(
      "http://localhost:8080/movies/title/" + name + "/" + page
    );
    const data = await response.text();
    return data;
  }

  async getMoviesByTitleSorted(
    page: number,
    orderBy: string,
    filterValue: string | undefined,
    isAscending: string
  ): Promise<string> {
    const response = await fetch(
      "http://localhost:8080/movies/title/" +
        filterValue +
        "/" +
        page +
        "/" +
        orderBy +
        "/" +
        isAscending
    );

    const data = await response.text();
    return data;
  }
}

export default MovieService;
