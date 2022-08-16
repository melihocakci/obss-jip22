import React from "react";
import UserService from "../service/UserService";

class Account extends React.Component {
  state = { data: {} };

  componentDidMount() {
    this.fetch();
  }

  fetch = async () => {
    const data = await UserService.fetchAuthUser();
    this.setState({ data: data });
  };

  render() {
    const { data } = this.state;
    return (
      <div>
        <h2>{data.username}</h2>
      </div>
    );
  }
}

export default Account;
