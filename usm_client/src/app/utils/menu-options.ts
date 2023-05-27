import { Notification } from '../models/notification.model';
import { DropDownMenuOption } from './../models/dropdown-menu-option.model';
import { ICONS_PATH } from './icon-constants';

export function exportToExcelMenu(): DropDownMenuOption[] {
  return [
    {
      label: 'Table',
      svgIcon: ICONS_PATH.excel,
      action: 1,
      icon: undefined,
    },
    {
      label: 'All',
      svgIcon: ICONS_PATH.excel,
      action: 2,
      icon: undefined,
    },
  ];
}

export function notificationsMenu(
  notifications: Notification[]
): DropDownMenuOption[] {
  let newMenuOptions = [];

  notifications.forEach((notification) => {
    const icon = notification.new
      ? ICONS_PATH.notificationUnread
      : ICONS_PATH.notificationRead;

    newMenuOptions.push(
      new DropDownMenuOption(notification.body, icon, notification.senderId)
    );
  });

  return newMenuOptions;
}

/**
 * Template for creating menu
 * Can be used mat-icon or svgIcon
 * 
   menu:any[]=[
    {label:"Vertebrates",icon:"add_a_photo",children:[
      {label:"Fishes",icon:"edit",children:[
          {label:"Baikal oilfish",icon:"assistant",action:1},
          {label:"Bala shark",icon:"grid_on",action:2},

            ]},
      {label:"Amphibians",svgIcon:"image",children:[
          {label:"Sonoran desert toad",svgIcon:"lens",action:3},
          {label:"Western toad",svgIcon:"music_note",action:4},
          {label:"Arroyo toad",svgIcon:"nature",action:5},
          {label:"Yosemite toad",svgIcon:"panorama_fish_eye",action:6},

      ]},
      {label:"Reptiles",icon:"timer",action:7},
      {label:"Birds",icon:"wb_sunny",action:8},
      {label:"Mammals",icon:"360",action:9}
      ]
    },
    {label:"Invertebrates",icon:"directions_car",children:[
      {label:"Insects",icon:"flight",action:10},
      {label:"Molluscs",icon:"local_bar",action:11},
      {label:"Crustaceans",icon:"local_florist",action:12}
      ]
    }
    ]
    action(value:any)
    {
      console.log(value)
    }
 */
