/*******************************************************************************
 * Copyright (c) 2003, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.ejb.ui.internal.wizard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;


/**
 * A ToolbarLayout-like layout for the palette. This layout is palette-specific and should not be
 * used externally. This layout only works when vertically oriented.
 * 
 * @author Pratik Shah
 */
public class PaletteToolbarLayout extends ToolbarLayout {

	private Rectangle[] sourceSizes;
	private Rectangle[] destinationSizes;
	private boolean scaling = false;
	private DrawerAnimationController controller;

	/**
	 * Constructor
	 * 
	 * @param controller
	 *            Can be <code>null</code> if no animation is desired
	 */
	public PaletteToolbarLayout(DrawerAnimationController controller) {
		super();
		this.controller = controller;
	}

	private void captureDestinationSizes(IFigure parent) {
		List children = parent.getChildren();
		destinationSizes = new Rectangle[children.size()];
		for (int i = 0; i < children.size(); i++) {
			destinationSizes[i] = ((IFigure) children.get(i)).getBounds().getCopy();
		}
	}

	private void captureSourceSizes(IFigure parent) {
		List children = parent.getChildren();
		sourceSizes = new Rectangle[children.size()];
		for (int i = 0; i < children.size(); i++) {
			sourceSizes[i] = ((IFigure) children.get(i)).getBounds().getCopy();
		}
	}

	/**
	 * A figure is growing if it's an expanded drawer.
	 * 
	 * @param child
	 *            The figure that is to be marked as growing or non-growing
	 * @return <code>true</code> if the given child is considered growing
	 */
	protected boolean isChildGrowing(IFigure child) {
		return child instanceof RelationshipDrawFigure && ((RelationshipDrawFigure) child).isExpanded();
	}

	private boolean isScaling() {
		return scaling;
	}

	/**
	 * @see org.eclipse.draw2d.ToolbarLayout#layout(org.eclipse.draw2d.IFigure)
	 */
	public void layout(IFigure parent) {
		if (isScaling()) {
			scaledLayout(parent);
			setScaling(controller.isAnimationInProgress());
		} else {
			if (controller != null && controller.isAnimationInProgress()) {
				captureSourceSizes(parent);
				normalLayout(parent);
				captureDestinationSizes(parent);
				setScaling(true);
			} else {
				normalLayout(parent);
			}
		}
	}

	private void normalLayout(IFigure parent) {
		List children = parent.getChildren();
		List childrenGrabbingVertical = new ArrayList();
		int numChildren = children.size();
		Rectangle clientArea = transposer.t(parent.getClientArea());
		int x = clientArea.x;
		int y = clientArea.y;
		int availableHeight = clientArea.height;
		boolean stretching;
		Dimension prefSizes[] = new Dimension[numChildren];
		Dimension minSizes[] = new Dimension[numChildren];
		int totalHeight = 0, totalMinHeight = 0, heightOfNonGrowingChildren = 0, heightPerChild = 0, excessHeight = 0;

		/*
		 * Determine hints.
		 */
		int wHint = isHorizontal() ? -1 : clientArea.width;
		int hHint = isHorizontal() ? clientArea.width : -1;

		/*
		 * Store the preferred and minimum sizes of all figures. Determine which figures can be
		 * stretched/shrunk.
		 */
		for (int i = 0; i < numChildren; i++) {
			IFigure child = (IFigure) children.get(i);

			prefSizes[i] = transposer.t(child.getPreferredSize(wHint, hHint));
			minSizes[i] = transposer.t(child.getMinimumSize(wHint, hHint));

			totalHeight += prefSizes[i].height;
			totalMinHeight += minSizes[i].height;

			if (isChildGrowing(child)) {
				childrenGrabbingVertical.add(child);
			} else {
				heightOfNonGrowingChildren += prefSizes[i].height;
			}
		}
		totalHeight += (numChildren - 1) * getSpacing();
		totalMinHeight += (numChildren - 1) * getSpacing();

		/*
		 * This is the algorithm that determines which figures need to be compressed/stretched and
		 * by how much.
		 */
		stretching = totalHeight - Math.max(availableHeight, totalMinHeight) < 0;
		// So long as there is at least one child that can be grown, figure out how much
		// height should be given to each growing child.
		if (!childrenGrabbingVertical.isEmpty()) {
			// We only want the last child to stretch. So, we remove all but the last
			// growing child. We add the preferred height of the children being marked
			// non-growing to heightOfNonGrowingChildren.
			if (stretching) {
				for (int i = 0; i < childrenGrabbingVertical.size() - 1; i++) {
					int index = children.indexOf(childrenGrabbingVertical.get(i));
					heightOfNonGrowingChildren += prefSizes[index].height;
				}
				Object last = childrenGrabbingVertical.get(childrenGrabbingVertical.size() - 1);
				childrenGrabbingVertical.clear();
				childrenGrabbingVertical.add(last);
			}

			boolean childrenDiscarded;
			// spaceToConsume is the space height available on the palette that is to be
			// shared by the growing children.
			int spaceToConsume = availableHeight - heightOfNonGrowingChildren;
			// heightPerChild is the height that each growing child is to be grown up to
			heightPerChild = spaceToConsume / childrenGrabbingVertical.size();
			// excessHeight is the space leftover at the bottom of the palette after each
			// growing child has been grown by heightPerChild.
			excessHeight = spaceToConsume - (heightPerChild * childrenGrabbingVertical.size());
			do {
				childrenDiscarded = false;
				for (Iterator iter = childrenGrabbingVertical.iterator(); iter.hasNext();) {
					IFigure childFig = (IFigure) iter.next();
					int i = childFig.getParent().getChildren().indexOf(childFig);
					boolean discardChild = false;
					if (stretching) {
						// In case of stretching, if the child's height is greater than
						// heightPerChild, mark that child as non-growing
						discardChild = prefSizes[i].height > heightPerChild;
					} else {
						// In case of shrinking, if the child's height is smaller than
						// heightPerChild, mark that child as non-growing
						discardChild = prefSizes[i].height < heightPerChild;
					}
					if (discardChild) {
						spaceToConsume -= prefSizes[i].height;
						heightOfNonGrowingChildren += prefSizes[i].height;
						childrenGrabbingVertical.remove(childFig);
						childrenDiscarded = true;
						heightPerChild = spaceToConsume / childrenGrabbingVertical.size();
						excessHeight = spaceToConsume - (heightPerChild * childrenGrabbingVertical.size());
						break;
					}
				}
			} while (childrenDiscarded);
		}

		/*
		 * Do the actual layout, i.e. set the bounds of all the figures.
		 */
		for (int i = 0; i < numChildren; i++) {
			IFigure child = (IFigure) children.get(i);

			Rectangle newBounds = new Rectangle(x, y, prefSizes[i].width, prefSizes[i].height);

			/*
			 * Giving the excess available space to the last child causes one problem -- when it's
			 * about to start compressing, the scrollbar might appear, then disappear, and then
			 * re-appear as you keep making the palette shorter pixel by pixel. But this bug is rare
			 * (three compressible drawers have to be expanded for this bug to appear for one pixel,
			 * four for two pixels, five for three, and so on), and hence can be ignored. Also, for
			 * this bug to occur, the last child would have to be a compressible drawer (one whose
			 * min height is not the same as its pref height).
			 */
			if (childrenGrabbingVertical.contains(child)) {
				// Set the height of growing children. If this is the last one, give it
				// the excess height.
				childrenGrabbingVertical.remove(child);
				if (childrenGrabbingVertical.isEmpty()) {
					newBounds.height = heightPerChild + excessHeight;
				} else {
					newBounds.height = heightPerChild;
				}
			}

			int minWidth = minSizes[i].width;
			int width = Math.min(prefSizes[i].width, child.getMaximumSize().width);
			if (getStretchMinorAxis())
				width = transposer.t(child.getMaximumSize()).width;
			width = Math.max(minWidth, Math.min(clientArea.width, width));
			newBounds.width = width;

			int adjust = clientArea.width - width;
			switch (getMinorAlignment()) {
				case ALIGN_TOPLEFT :
					adjust = 0;
					break;
				case ALIGN_CENTER :
					adjust /= 2;
					break;
				case ALIGN_BOTTOMRIGHT :
					break;
			}
			newBounds.x += adjust;
			child.setBounds(transposer.t(newBounds));
			y += newBounds.height + getSpacing();
		}
	}

	private void scaledLayout(IFigure parent) {
		List children = parent.getChildren();
		float progress = controller.getAnimationProgress();

		for (int i = 0; i < children.size(); i++) {
			Rectangle rect1 = sourceSizes[i];
			Rectangle rect2 = destinationSizes[i];
			IFigure child = (IFigure) children.get(i);

			child.setBounds(new Rectangle(Math.round(progress * rect2.x + (1 - progress) * rect1.x), Math.round(progress * rect2.y + (1 - progress) * rect1.y), Math.round(progress * rect2.width + (1 - progress) * rect1.width), Math.round(progress * rect2.height + (1 - progress) * rect1.height)));
		}
	}

	private void setScaling(boolean newVal) {
		scaling = newVal;
		if (!scaling) {
			sourceSizes = null;
			destinationSizes = null;
		}
	}

}