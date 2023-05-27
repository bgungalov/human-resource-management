import {
  Component,
  OnInit,
  ViewChildren,
  Input,
  Output,
  AfterViewInit,
  QueryList,
  EventEmitter,
} from '@angular/core';
import { MatMenu } from '@angular/material/menu';

/**
 * Renders the menu component (button menu).
 */
@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss'],
})
export class MenuComponent implements OnInit, AfterViewInit {
  @Input() title: string;
  @Input() menuData: any[];
  @Output() action: EventEmitter<any> = new EventEmitter<any>();

  //we are going to get all "mat-menu" using viewChildren
  @ViewChildren(MatMenu) matMenus: QueryList<MatMenu>;
  menuItems: string[] = [];
  yet: boolean = false;
  submenus: any[] = [];

  /**
   * The function takes a value as an argument, and emits that value to the parent component
   * @param {any} value - any - The value that will be passed to the parent component when the button
   * is clicked.
   */
  onClick(value: any) {
    this.action.emit(value);
  }

  /**
   * It returns the menu at the specified index, if it exists
   * @param {number} index - number - the index of the menu to get
   * @returns The menu at the given index.
   */
  getMenu(index: number) {
    return index >= 0 && this.matMenus
      ? this.matMenus.find((x, i) => i == index)
      : null;
  }

  /**
   * For each submenu, for each item in the submenu, if the item has a submenu, set the submenu to the
   * index of the item's action in the menuItems array
   */
  reindex() {
    this.submenus.forEach((menu) => {
      menu.forEach((x: any) => {
        if (x.subMenu != -1) x.subMenu = this.menuItems.indexOf(x.action);
      });
    });
  }

  /**
   * It takes a menu, a prefix, and a count, and then it adds the prefix to the menuItems array, and
   * then it adds an object to the submenus array that contains the label, icon, svgIcon, action, and
   * subMenu properties of the menu, and then it calls itself for each child of the menu, and then it
   * returns the menuItems and submenus arrays
   * @param {any[]} menu - any[] - the menu array
   * @param {string} prefix - the prefix of the submenu
   * @param {number} count - number - this is the number of submenus that have been created.
   */
  createSubmenus(menu: any[], prefix: string, count: number) {
    //add to the array menuItems the "prefix"
    this.menuItems.push(prefix);
    //add to submenu an object to create the submenu
    this.submenus.push(
      menu.map((x: any, index: number) => ({
        label: x.label,
        icon: x.icon,
        svgIcon: x.svgIcon,
        action:
          x.children === null || x.children === undefined
            ? x.action
            : prefix + index,
        subMenu: x.children === null || x.children === undefined ? -1 : 0,
      }))
    );

    //if has children call the function for each child
    menu.forEach((x: any, index: number) => {
      if (x.children) {
        this.createSubmenus(x.children, prefix + index, count + 1);
      }
    });
  }

  ngOnInit() {
    this.createSubmenus(this.menuData, 's0', 1);
    this.reindex();
  }

  ngAfterViewInit() {
    //this avoid Angular give us errors, only when repaint the menu
    //we asign the [matMenutiggerFor]
    setTimeout(() => {
      this.yet = true;
    });
  }
}
