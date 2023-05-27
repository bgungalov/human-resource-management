export class DropDownMenuOption {
  label: string;
  svgIcon?: string;
  icon: string;
  action: any;

  constructor(label: string, icon: string, action: any, svgIcon?: string) {
    this.label = label;
    this.svgIcon = svgIcon;
    this.icon = icon;
    this.action = action;
  }
}
