class ReviewService {
  async getMostRecentByMovieId(id: string) {
    const response = await fetch("http://localhost:8080/review/movie/recent/" + id);
    const data = await response.text();
    return data;
  }
}

export default ReviewService;