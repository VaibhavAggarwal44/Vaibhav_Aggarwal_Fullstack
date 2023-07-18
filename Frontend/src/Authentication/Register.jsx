import { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "./styles.css";

import Image from "react-bootstrap/Image";

const Register = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [cpassword, setCpassword] = useState("");

  useEffect(() => {
    localStorage.clear();
  }, []);

  const usenavigate = useNavigate();

  function hasWhiteSpace(s) {
    return s.indexOf(" ") >= 0;
  }

  const IsValidate = () => {
    let isproceed = true;
    let errormessage = "Please enter the value in ";
    if (username === null || username === "") {
      isproceed = false;
      errormessage += " Username,";
    }
    if (hasWhiteSpace(username)) {
      isproceed = false;
      toast.warning("enter valid username");
    }
    if(username.length<4){
      isproceed=false;
      toast.warning("username too short")
    }
    if(username.length>16){
      isproceed=false
      toast.warning("username too long")
    }
    if (password === null || password === "") {
      isproceed = false;
      errormessage += " Password,";
    }
    if (hasWhiteSpace(password)) {
      isproceed = false;
      toast.warning("enter password without space");
    }
    if(password.length<4){
      isproceed=false;
      toast.warning("password too short")
    }
    if(password.length>15){
      isproceed=false;
      toast.warning("password too long")
    }
    if (cpassword === null || cpassword === "") {
      isproceed = false;
      errormessage = "Please Confirm your Password,";
    }
    if (cpassword !== password) {
      isproceed = false;
      errormessage = "password mismatch";
    }

    if (!isproceed) {
      toast.warning(errormessage);
    }
    return isproceed;
  };

  const handlesubmit = (e) => {
    e.preventDefault();

    if (IsValidate()) {
      fetch("http://localhost:8081/auth/register", {
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
          if (resp.username == "AaA" || resp.password == "AaA") {
            toast.error("user already exists");
          } else {
            localStorage.setItem("username", username);
            toast.success("Successfully registered");
            usenavigate("/");
          }
        });
    }
  };
  return (
    <>
      <div className="offset-lg-3 col-lg-6" style={{ marginTop: "100px" }}>
        <Image src={require("../download.png")} fluid />
        <form onSubmit={handlesubmit}>
          <div className="card">
            <div className="card-header">
              <h2>User Registration</h2>
            </div>
            <div className="card-body">
              <div className="row">
                <div>
                  <div className="form-group">
                    <label>
                      User Name <span className="errmsg">*</span>
                    </label>
                    <input
                      value={username}
                      onChange={(e) => setUsername(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
                <div>
                  <div className="form-group">
                    <label>
                      Password <span className="errmsg">*</span>
                    </label>
                    <input
                      value={password}
                      onChange={(e) => setPassword(e.target.value)}
                      type="password"
                      className="form-control"
                    ></input>
                  </div>
                </div>
                <div>
                  <div className="form-group">
                    <label>
                      Confirm Password<span className="errmsg">*</span>
                    </label>
                    <input
                      value={cpassword}
                      onChange={(e) => setCpassword(e.target.value)}
                      type="password"
                      className="form-control"
                    ></input>
                  </div>
                </div>
              </div>
            </div>
            <div className="card-footer">
              <button type="submit" className="btn btn-primary button-style">
                Register
              </button>{" "}
              |
              <Link to={"/login"} className="btn btn-danger">
                Login
              </Link>
            </div>
          </div>
        </form>
      </div>

      <ToastContainer />
    </>
  );
};

export default Register;
