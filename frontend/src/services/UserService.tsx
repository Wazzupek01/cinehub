class UserService {
  async addToWatchlist(movieId: string){
    var headers = new Headers();
    headers.append('Content-Type', 'application/json');

    const response = await fetch("http://localhost:8080/user/watchLater/add/" + movieId, {
      method: "POST",
      headers: headers,
      mode: "cors",
      credentials: "include",
      redirect: "follow"
    });

    return response.status;
  }

  async removeFromWatchlist(movieId: string){
    var headers = new Headers();
    headers.append('Content-Type', 'application/json');

    const response = await fetch("http://localhost:8080/user/watchLater/remove/" + movieId, {
      method: "DELETE",
      headers: headers,
      mode: "cors",
      credentials: "include",
      redirect: "follow"
    });

    return response.status;
  }

  async getUserByNickname(nickname: string){
    const response = await fetch("http://localhost:8080/user/nickname/" + nickname, {
      method: "GET",
      redirect: "follow"
    });

    return response.json();
  }
}

export default UserService;