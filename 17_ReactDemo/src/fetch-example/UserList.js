import React, { useEffect, useState } from "react";
import lodash from "lodash";
import "antd/dist/antd.css";
import axios from "axios";
import { dataSource, columns, Table } from "antd";
import fetchUsers from "./UserService";

export default (props) => {
    const [data, setData] = useState(null);
    const [pagination, setPagination] = useState({ pageSize: 10, current: 1 });
    const [loading, setLoading] = useState(true);

    const fetch = async (params) => {
        setLoading(true);
        const response = await fetchUsers(params);
        setData(response.data.results);
        setLoading(false);
        setPagination({ pagination: { ...params.pagination, total: 200 } });
    };

    useEffect(() => {
        fetch({ pagination });
    }, []);

    const handleOnChange = (pagination, filters, sorter) => {
        fetch({ pagination, sortField: sorter.field, sortOrder: sorter.order, ...filters });
    };

    const columns = [
        {
            title: "Name",
            dataIndex: "name",
            sorter: true,
            render: (name) => name.first + " " + name.last,
        },
        {
            title: "Gender",
            filters: [
                { text: "Male", value: "male" },
                { text: "Female", value: "female" },
            ],
            dataIndex: "gender",
        },
        {
            title: "Email",
            dataIndex: "email",
        },
    ];

    return (
        <Table
            dataSource={data}
            columns={columns}
            loading={loading}
            rowKey={(record) => record.login.uuid}
            pagination={pagination}
            onChange={handleOnChange}
        />
    );
};
