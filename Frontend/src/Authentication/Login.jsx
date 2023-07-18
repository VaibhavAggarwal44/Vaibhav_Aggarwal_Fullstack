import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import Image from "react-bootstrap/Image";
import "./styles.css";
import Navbar from "../NavBar/Navbar";

const Login = () => {
  const [username, usernameupdate] = useState("");
  const [password, passwordupdate] = useState("");

  const usenavigate = useNavigate();

  useEffect(() => {
    localStorage.clear();
  }, []);

  const ProceedLogin = (e) => {
    e.preventDefault();
    if (validate()) {
      fetch("http://localhost:8081/auth/login", {
        method: "POST",
        headers: {
          Accept: "application/json, text/plain",
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          username: username,
          password: password,
        }),
      })
        .then((resp) => {
          return resp.json();
        })
        .then((resp) => {
          console.log(resp);
          if (resp.username == "" || resp.password == "") {
            toast.error("either user does not exists or password is wrong");
          } else {
            localStorage.setItem("username", username);
            toast.success("Successfully logged in");
            usenavigate("/");
          }
        });
    }
  };

  function hasWhiteSpace(s) {
    return s.indexOf(" ") >= 0;
  }

  const validate = () => {
    let result = true;
    console.log("yes");
    if (username === "" || username === null) {
      result = false;
      toast.warning("Please Enter Username");
    }
    if (hasWhiteSpace(username)) {
      result = false;
      toast.warning("enter valid username");
    }
    if (password === "" || password === null) {
      result = false;
      toast.warning("Please Enter Password");
    }
    return result;
  };
  return (
    <>
      <div className="row">
        <div className="offset-lg-3 col-lg-6" style={{ marginTop: "100px" }}>
          <Image src={require("../download.png")} fluid />
          <form onSubmit={ProceedLogin} className="container">
            <div className="card">
              <div className="card-header">
                <h2>User Login</h2>
              </div>
              <div className="card-body">
                <div className="form-group">
                  <label>
                    User Name <span className="errmsg">*</span>
                  </label>
                  <input
                    value={username}
                    onChange={(e) => usernameupdate(e.target.value)}
                    className="form-control"
                  ></input>
                </div>
                <div className="form-group">
                  <label>
                    Password <span className="errmsg">*</span>
                  </label>
                  <input
                    type="password"
                    value={password}
                    onChange={(e) => passwordupdate(e.target.value)}
                    className="form-control"
                  ></input>
                </div>
              </div>
              <div className="card-footer">
                <button type="submit" className="btn btn-primary button-style">
                  Login
                </button>{" "}
                |
                <Link className="btn btn-success" to={"/register"}>
                  New User
                </Link>
              </div>
            </div>
          </form>
        </div>
        <ToastContainer />
      </div>
    </>
  );
};

export default Login;
