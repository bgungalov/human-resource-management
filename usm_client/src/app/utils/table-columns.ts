import { TableColumn } from '../models/table-column.model';

export function getUserColumns(): TableColumn[] {
  return [
    {
      name: 'ID',
      dataKey: 'id',
      position: 'left',
      isSortable: false,
      isClickable: true,
    },
    {
      name: 'First Name',
      dataKey: 'firstName',
      position: 'left',
      isSortable: false,
      isClickable: false,
    },
    {
      name: 'Last Name',
      dataKey: 'lastName',
      position: 'left',
      isSortable: false,
      isClickable: false,
    },
  ];
}

export function getActionsColumns(): TableColumn[] {
  return [
    {
      name: 'ID',
      dataKey: 'id',
      position: 'left',
      isSortable: false,
      isClickable: false,
    },
    {
      name: 'User',
      dataKey: 'user',
      position: 'left',
      isSortable: false,
      isClickable: false,
    },
    {
      name: 'IP',
      dataKey: 'ip',
      position: 'left',
      isSortable: false,
      isClickable: false,
    },
    {
      name: 'Method',
      dataKey: 'method',
      position: 'left',
      isSortable: false,
      isClickable: false,
    },
    {
      name: 'URL',
      dataKey: 'url',
      position: 'left',
      isSortable: false,
      isClickable: false,
    },
    {
      name: 'Page',
      dataKey: 'page',
      position: 'left',
      isSortable: false,
      isClickable: false,
    },
    {
      name: 'Query String',
      dataKey: 'queryString',
      position: 'left',
      isSortable: false,
      isClickable: false,
    },
    {
      name: 'Referer Page',
      dataKey: 'refererPage',
      position: 'left',
      isSortable: false,
      isClickable: false,
    },
    {
      name: 'User Agent',
      dataKey: 'userAgent',
      position: 'left',
      isSortable: false,
      isClickable: false,
    },
    {
      name: 'Logged Time',
      dataKey: 'loggedTime',
      position: 'left',
      isSortable: true,
      isClickable: false,
    },
    {
      name: 'Unique Visit',
      dataKey: 'uniqueVisit',
      position: 'left',
      isSortable: false,
      isClickable: false,
    },
  ];
}

export function getStatusHealthDetails(): TableColumn[] {
  return [
    {
      name: 'Component',
      dataKey: 'name',
      position: 'left',
      isSortable: false,
      isClickable: true,
    },
    {
      name: 'Status',
      dataKey: 'status',
      position: 'left',
      isSortable: false,
      isClickable: false,
    },
    {
      name: 'Details',
      dataKey: 'details',
      position: 'left',
      isSortable: false,
      isClickable: false,
    },
  ];
}
