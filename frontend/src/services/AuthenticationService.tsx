class AuthenticationService {
  async loginUser(email: string, password: string) {
    var headers = new Headers();
    headers.append('Content-Type', 'application/json');

    const response = await fetch("http://localhost:8080/auth/authenticate", {
      method: "POST",
      headers: headers,
      mode: "cors",
      credentials: "include",
      redirect: "follow",
      body: JSON.stringify({
        "email": email,
        "password": password
      })
    });

    return response.text();
  }

  async registerUser(nickname: string, email: string, password: string) {
    var headers = new Headers();
    headers.append('Content-Type', 'application/json');

    const response = await fetch("http://localhost:8080/auth/register", {
      method: "POST",
      headers: headers,
      mode: "cors",
      credentials: "include",
      redirect: "follow",
      body: JSON.stringify({
        "nickname": nickname,
        "email": email,
        "password": password
      })
    });

    return response.text();
  }

  async logout(){
    var headers = new Headers();
    headers.append('Content-Type', 'application/json');

    const response = await fetch("http://localhost:8080/auth/logout", {
      method: "GET",
      headers: headers,
      mode: "cors",
      credentials: "include",
      redirect: "follow"
    });

    return response.status;
  }
}

export default AuthenticationService;
