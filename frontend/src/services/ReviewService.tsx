class ReviewService {
  async getMostRecentByMovieId(id: string) {
    const response = await fetch("http://localhost:8080/review/movie/recent/" + id);
    const data = await response.text();
    return data;
  }

  removeReviewById(id: string){
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');

    fetch("http://localhost:8080/review/delete/" + id, {
      method: "DELETE",
      headers: headers,
      mode: "cors",
      credentials: "include",
      redirect: "follow"
    })
  }

  async addReview(rating: number, content: string, movieId: string){
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');

    const response = await fetch("http://localhost:8080/review/add", {
      method: "POST",
      headers: headers,
      mode: "cors",
      credentials: "include",
      redirect: "follow",
      body: JSON.stringify({
        "rating": rating,
        "content": content,
        "movieId": movieId
      })
    });

    return response.text();
  }
}

export default ReviewService;