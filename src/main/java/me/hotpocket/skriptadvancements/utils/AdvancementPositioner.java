package me.hotpocket.skriptadvancements.utils;

import com.google.common.collect.Lists;
import me.hotpocket.skriptadvancements.utils.creation.FakeAdvancement;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

public class AdvancementPositioner {

   private final FakeAdvancement advancement;
   @org.jetbrains.annotations.Nullable
   private final AdvancementPositioner parent;
   @org.jetbrains.annotations.Nullable
   private final AdvancementPositioner previousSibling;
   private final int childrenSize;
   private final List<AdvancementPositioner> children = Lists.newArrayList();
   private AdvancementPositioner optionalLast;
   @org.jetbrains.annotations.Nullable
   private AdvancementPositioner substituteChild;
   private int depth;
   private float row;
   private float relativeRowInSiblings;
   private float field_1266;
   private float field_1265;

   public AdvancementPositioner(FakeAdvancement advancement, @org.jetbrains.annotations.Nullable AdvancementPositioner parent, @org.jetbrains.annotations.Nullable AdvancementPositioner previousSibling, int childrenSize, int depth) {
      if (advancement.getDisplay() == null) {
         throw new IllegalArgumentException("Can't position an invisible advancement!");
      } else {
         this.advancement = advancement;
         this.parent = parent;
         this.previousSibling = previousSibling;
         this.childrenSize = childrenSize;
         this.optionalLast = this;
         this.depth = depth;
         this.row = -1.0F;
         AdvancementPositioner advancementPositioner = null;

         FakeAdvancement placedAdvancement;
         for (Iterator var7 = advancement.getChildren().iterator(); var7.hasNext(); advancementPositioner = this.findChildrenRecursively(placedAdvancement, advancementPositioner)) {
            placedAdvancement = (FakeAdvancement) var7.next();
         }

      }
   }

   @org.jetbrains.annotations.Nullable
   private AdvancementPositioner findChildrenRecursively(FakeAdvancement advancement, @org.jetbrains.annotations.Nullable AdvancementPositioner lastChild) {
      FakeAdvancement placedAdvancement;
      if (advancement.getDisplay() != null) {
         lastChild = new AdvancementPositioner(advancement, this, lastChild, this.children.size() + 1, this.depth + 1);
         this.children.add(lastChild);
      } else {
         for (Iterator var3 = advancement.getChildren().iterator(); var3.hasNext(); lastChild = this.findChildrenRecursively(placedAdvancement, lastChild)) {
            placedAdvancement = (FakeAdvancement) var3.next();
         }
      }

      return lastChild;
   }

   private void calculateRecursively() {
      if (this.children.isEmpty()) {
         if (this.previousSibling != null) {
            this.row = this.previousSibling.row + 1.0F;
         } else {
            this.row = 0.0F;
         }

      } else {
         AdvancementPositioner advancementPositioner = null;

         AdvancementPositioner advancementPositioner2;
         for (Iterator var2 = this.children.iterator(); var2.hasNext(); advancementPositioner = advancementPositioner2.onFinishCalculation(advancementPositioner == null ? advancementPositioner2 : advancementPositioner)) {
            advancementPositioner2 = (AdvancementPositioner) var2.next();
            advancementPositioner2.calculateRecursively();
         }

         this.onFinishChildrenCalculation();
         float f = (((AdvancementPositioner) this.children.get(0)).row + ((AdvancementPositioner) this.children.get(this.children.size() - 1)).row) / 2.0F;
         if (this.previousSibling != null) {
            this.row = this.previousSibling.row + 1.0F;
            this.relativeRowInSiblings = this.row - f;
         } else {
            this.row = f;
         }

      }
   }

   private float findMinRowRecursively(float deltaRow, int depth, float minRow) {
      this.row += deltaRow;
      this.depth = depth;
      if (this.row < minRow) {
         minRow = this.row;
      }

      AdvancementPositioner advancementPositioner;
      for (Iterator var4 = this.children.iterator(); var4.hasNext(); minRow = advancementPositioner.findMinRowRecursively(deltaRow + this.relativeRowInSiblings, depth + 1, minRow)) {
         advancementPositioner = (AdvancementPositioner) var4.next();
      }

      return minRow;
   }

   private void increaseRowRecursively(float deltaRow) {
      this.row += deltaRow;
      Iterator var2 = this.children.iterator();

      while (var2.hasNext()) {
         AdvancementPositioner advancementPositioner = (AdvancementPositioner) var2.next();
         advancementPositioner.increaseRowRecursively(deltaRow);
      }

   }

   private void onFinishChildrenCalculation() {
      float f = 0.0F;
      float g = 0.0F;

      for (int i = this.children.size() - 1; i >= 0; --i) {
         AdvancementPositioner advancementPositioner = (AdvancementPositioner) this.children.get(i);
         advancementPositioner.row += f;
         advancementPositioner.relativeRowInSiblings += f;
         g += advancementPositioner.field_1266;
         f += advancementPositioner.field_1265 + g;
      }

   }

   @org.jetbrains.annotations.Nullable
   private AdvancementPositioner getFirstChild() {
      if (this.substituteChild != null) {
         return this.substituteChild;
      } else {
         return !this.children.isEmpty() ? (AdvancementPositioner) this.children.get(0) : null;
      }
   }

   @Nullable
   private AdvancementPositioner getLastChild() {
      if (this.substituteChild != null) {
         return this.substituteChild;
      } else {
         return !this.children.isEmpty() ? (AdvancementPositioner) this.children.get(this.children.size() - 1) : null;
      }
   }

   private AdvancementPositioner onFinishCalculation(AdvancementPositioner last) {
      if (this.previousSibling == null) {
         return last;
      } else {
         AdvancementPositioner advancementPositioner = this;
         AdvancementPositioner advancementPositioner2 = this;
         AdvancementPositioner advancementPositioner3 = this.previousSibling;
         AdvancementPositioner advancementPositioner4 = (AdvancementPositioner) this.parent.children.get(0);
         float f = this.relativeRowInSiblings;
         float g = this.relativeRowInSiblings;
         float h = advancementPositioner3.relativeRowInSiblings;

         float i;
         for (i = advancementPositioner4.relativeRowInSiblings; advancementPositioner3.getLastChild() != null && advancementPositioner.getFirstChild() != null; g += advancementPositioner2.relativeRowInSiblings) {
            advancementPositioner3 = advancementPositioner3.getLastChild();
            advancementPositioner = advancementPositioner.getFirstChild();
            advancementPositioner4 = advancementPositioner4.getFirstChild();
            advancementPositioner2 = advancementPositioner2.getLastChild();
            advancementPositioner2.optionalLast = this;
            float j = advancementPositioner3.row + h - (advancementPositioner.row + f) + 1.0F;
            if (j > 0.0F) {
               advancementPositioner3.getLast(this, last).pushDown(this, j);
               f += j;
               g += j;
            }

            h += advancementPositioner3.relativeRowInSiblings;
            f += advancementPositioner.relativeRowInSiblings;
            i += advancementPositioner4.relativeRowInSiblings;
         }

         if (advancementPositioner3.getLastChild() != null && advancementPositioner2.getLastChild() == null) {
            advancementPositioner2.substituteChild = advancementPositioner3.getLastChild();
            advancementPositioner2.relativeRowInSiblings += h - g;
         } else {
            if (advancementPositioner.getFirstChild() != null && advancementPositioner4.getFirstChild() == null) {
               advancementPositioner4.substituteChild = advancementPositioner.getFirstChild();
               advancementPositioner4.relativeRowInSiblings += f - i;
            }

            last = this;
         }

         return last;
      }
   }

   private void pushDown(AdvancementPositioner positioner, float extraRowDistance) {
      float f = (float) (positioner.childrenSize - this.childrenSize);
      if (f != 0.0F) {
         positioner.field_1266 -= extraRowDistance / f;
         this.field_1266 += extraRowDistance / f;
      }

      positioner.field_1265 += extraRowDistance;
      positioner.row += extraRowDistance;
      positioner.relativeRowInSiblings += extraRowDistance;
   }

   private AdvancementPositioner getLast(AdvancementPositioner advancementPositioner, AdvancementPositioner advancementPositioner2) {
      return this.optionalLast != null && advancementPositioner.parent.children.contains(this.optionalLast) ? this.optionalLast : advancementPositioner2;
   }

   private void apply() {
      if (this.advancement.getDisplay() != null) {
         this.advancement.getDisplay().setX((float) this.depth);
         this.advancement.getDisplay().setY(this.row);
      }
      if (!this.children.isEmpty()) {
         Iterator var1 = this.children.iterator();

         while (var1.hasNext()) {
            AdvancementPositioner advancementPositioner = (AdvancementPositioner) var1.next();
            advancementPositioner.apply();
         }
      }

   }

   public static void arrangeForTree(FakeAdvancement root) {
      if (root.getDisplay() == null) {
         throw new IllegalArgumentException("Can't position children of an invisible root!");
      } else {
         AdvancementPositioner advancementPositioner = new AdvancementPositioner(root, (AdvancementPositioner) null, (AdvancementPositioner) null, 1, 0);
         advancementPositioner.calculateRecursively();
         float f = advancementPositioner.findMinRowRecursively(0.0F, 0, advancementPositioner.row);
         if (f < 0.0F) {
            advancementPositioner.increaseRowRecursively(-f);
         }

         advancementPositioner.apply();
      }
   }

}