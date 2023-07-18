import React from "react";
import { useState } from "react";
import { useEffect } from "react";
import {
  Button,
  Card,
  CardBody,
  CardText,
  Col,
  Container,
  Input,
  Row,
} from "reactstrap";

function Reply(props) {
  const [comment, setComment] = useState([]);
  useEffect(() => {
    let commentId = props.commentId;

    fetch(`http://localhost:8081/comment/${commentId}/f`)
      .then((response) => {
        // console.log(response.json());
        return response.json();
      })
      .then((data) => {
        setComment(data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  return (
    <>
      {comment.replies && <h5>Replies: {comment.replies.length}</h5>}
      {comment.replies &&
        comment.replies.map((reply) => {
          return (
            <>
              <Card
                className="mt-4 border-left-3 border-dark border-top-0 border-right-0 border-bottom-0 bg-light"
                key={reply.commentId}
              >
                <CardBody>
                  <div className="d-flex">
                    <CardText className="mx-2">
                      <strong>{reply.username}</strong>
                    </CardText>
                    <CardText>{reply.content}</CardText>
                  </div>
                </CardBody>
              </Card>
            </>
          );
        })}
    </>
  );
}

export default Reply;
