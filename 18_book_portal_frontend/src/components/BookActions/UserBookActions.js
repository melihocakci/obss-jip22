import React, { useEffect } from "react";
import { useParams } from "react-router-dom";
import UserService from "../../service/UserService";
import { useState } from "react";
import { Typography, Spin, Divider, Button, message } from "antd";
const { Title } = Typography;

export default () => {
  const [read, setRead] = useState();
  const [favorite, setFavorite] = useState();
  const [loading, setLoading] = useState(true);
  const { id: bookId } = useParams();

  useEffect(() => {
    fetch();
  }, []);

  const fetch = async () => {
    setLoading(true);
    const response = await UserService.fetchThisUser();

    if (!response.success) {
      message.error(response.message);
      return;
    }

    const user = response.body;

    let isRead = false;
    for (let book of user.readBooks) {
      if (book.id == bookId) {
        isRead = true;
        break;
      }
    }
    setRead(isRead);

    let isFavorite = false;
    for (let book of user.favoriteBooks) {
      if (book.id == bookId) {
        isFavorite = true;
        break;
      }
    }
    setFavorite(isFavorite);

    setLoading(false);
  };

  const toggleRead = async () => {
    if (!read) {
      const response = await UserService.addRead(bookId);

      if (!response.success) {
        message.error(response.message);
        return;
      }

      message.success("Added to read books");
    } else {
      const response = await UserService.removeRead(bookId);

      if (!response.success) {
        message.error(response.message);
        return;
      }

      message.success("Removed from read books");
    }

    fetch();
  };

  const toggleFavorite = async () => {
    if (!favorite) {
      const response = await UserService.addFavorite(bookId);

      if (!response.success) {
        message.error(response.message);
        return;
      }

      message.success("Added to favorite books");
    } else {
      const response = await UserService.removeFavorite(bookId);

      if (!response.success) {
        message.error(response.message);
        return;
      }

      message.success("Removed from favorite books");
    }

    fetch();
  };

  if (loading) {
    return (
      <div class="actions">
        <Spin />
      </div>
    );
  }

  return (
    <div class="actions">
      <Divider orientation="left">
        <Title level={5}>Actions</Title>
      </Divider>
      <Button onClick={toggleRead}>
        {read ? "Remove from read list" : "Add to read list"}
      </Button>
      <Button onClick={toggleFavorite}>
        {favorite ? "Remove from favorite list" : "Add to favorite list"}
      </Button>
    </div>
  );
};
