/* AppLink is a class that has a constructor that takes in a path, title, component, icon, and
navigateTo, and sets the class properties to the values of the constructor parameters. */
export class AppLink {
  path: string;
  title: string;
  component: any;
  icon: any;
  navigateTo: string;

  constructor(
    path: string,
    title: string,
    component: any,
    icon?: any,
    navigateTo?: string
  ) {
    this.path = path;
    this.title = title;
    this.component = component;
    this.icon = icon;
    this.navigateTo = navigateTo;
  }
}
