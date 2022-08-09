import React from "react";
import "antd/dist/antd.css";
import { Table } from "antd";
import UserService from "../service/UserService";

const columns = [
    {
        title: "Username",
        dataIndex: "username",
        sorter: true,
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

        console.log(JSON.stringify(data));

        this.setState({
            loading: false,
            data: data,
            pagination: {
                ...params.pagination,
                total: 200, // Mock data
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
