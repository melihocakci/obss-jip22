import React from "react";
import "antd/dist/antd.css";
import { Table } from "antd";
import UserService from "../service/UserService";
import { Link } from "react-router-dom";

const columns = [
    {
        title: "Username",
        dataIndex: "username",
        sorter: true,
        width: "20%",
    },
    {
        title: "Read Books",
        dataIndex: "readBooks",
        render: (list) => list.length,
        width: "20%",
    },
    {
        title: "Favorited Books",
        dataIndex: "favoriteBooks",
        render: (list) => list.length,
        width: "20%",
    },
    {
        title: "Profile",
        dataIndex: "id",
        render: (id) => <Link to={"/users/" + id}>See Profile</Link>,
        width: "20%",
    },
];

class PersonList extends React.Component {
    state = {
        data: [],
        pagination: {
            current: 1,
            pageSize: 10,
        },
        loading: false,
    };

    componentDidMount() {
        const { pagination } = this.state;
        this.fetch({ pagination });
    }

    handleTableChange = (pagination, filters, sorter) => {
        this.fetch({
            sortField: sorter.field,
            sortOrder: sorter.order,
            pagination,
            ...filters,
        });
    };

    fetch = async (params = {}) => {
        this.setState({ loading: true });

        const data = await UserService.fetchUsers(params);
        const userCount = await UserService.fetchUserCount();

        console.log(JSON.stringify(data));

        this.setState({
            loading: false,
            data: data,
            pagination: {
                ...params.pagination,
                total: userCount, // Mock data
            },
        });
    };

    render() {
        const { data, pagination, loading } = this.state;
        return (
            <Table
                columns={columns}
                rowKey={(record) => record.id}
                dataSource={data}
                pagination={pagination}
                loading={loading}
                onChange={this.handleTableChange}
            />
        );
    }
}

export default PersonList;
