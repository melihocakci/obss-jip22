import { Typography } from "antd";
import { useContext } from "react";
import UserContext from "../context/UserContext";
const { Title } = Typography;

const Home = () => {
  return (
    <div>
      <Title>Welcome to MyBookList!</Title>
      <h3>The best place to share your reading progress!</h3>
    </div>
  );
};

export default Home;
