// Generated by delombok at Sun Aug 23 13:17:03 CEST 2015
package ast;

import lombok.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ast.Ast.Doc;
import ast.Ast.MethodSelector;
import ast.Ast.Path;
import ast.Ast.Stage;
import ast.ExpCore.*;
import ast.ExpCore.ClassB.Member;

public class Util {
	
	
	public static final class InfoAboutMs {
		@NonNull
		private final java.util.List<Path> allSuper;
		@NonNull
		private final Path original;
		@NonNull
		private final ast.Ast.MethodType mt;
		
		@java.beans.ConstructorProperties({"allSuper", "original", "mt"})
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public InfoAboutMs(@NonNull final java.util.List<Path> allSuper, @NonNull final Path original, @NonNull final ast.Ast.MethodType mt) {
			if (allSuper == null) {
				throw new java.lang.NullPointerException("allSuper");
			}
			if (original == null) {
				throw new java.lang.NullPointerException("original");
			}
			if (mt == null) {
				throw new java.lang.NullPointerException("mt");
			}
			this.allSuper = allSuper;
			this.original = original;
			this.mt = mt;
		}
		
		@NonNull
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public java.util.List<Path> getAllSuper() {
			return this.allSuper;
		}
		
		@NonNull
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public Path getOriginal() {
			return this.original;
		}
		
		@NonNull
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public ast.Ast.MethodType getMt() {
			return this.mt;
		}
		
		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public boolean equals(final java.lang.Object o) {
			if (o == this) return true;
			if (!(o instanceof Util.InfoAboutMs)) return false;
			final InfoAboutMs other = (InfoAboutMs)o;
			final java.lang.Object this$allSuper = this.getAllSuper();
			final java.lang.Object other$allSuper = other.getAllSuper();
			if (this$allSuper == null ? other$allSuper != null : !this$allSuper.equals(other$allSuper)) return false;
			final java.lang.Object this$original = this.getOriginal();
			final java.lang.Object other$original = other.getOriginal();
			if (this$original == null ? other$original != null : !this$original.equals(other$original)) return false;
			final java.lang.Object this$mt = this.getMt();
			final java.lang.Object other$mt = other.getMt();
			if (this$mt == null ? other$mt != null : !this$mt.equals(other$mt)) return false;
			return true;
		}
		
		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int hashCode() {
			final int PRIME = 59;
			int result = 1;
			final java.lang.Object $allSuper = this.getAllSuper();
			result = result * PRIME + ($allSuper == null ? 0 : $allSuper.hashCode());
			final java.lang.Object $original = this.getOriginal();
			result = result * PRIME + ($original == null ? 0 : $original.hashCode());
			final java.lang.Object $mt = this.getMt();
			result = result * PRIME + ($mt == null ? 0 : $mt.hashCode());
			return result;
		}
		
		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public java.lang.String toString() {
			return "Util.InfoAboutMs(allSuper=" + this.getAllSuper() + ", original=" + this.getOriginal() + ", mt=" + this.getMt() + ")";
		}
	}
	
	public static final class CsMx {
		@NonNull
		private final java.util.List<String> cs;
		@NonNull
		private final MethodSelector ms;
		
		public String toString() {
			String prefix = "Outer0";
			if (!cs.isEmpty()) {
				prefix = String.join("::", cs);
			}
			return prefix + "." + ms;
		}
		
		@java.beans.ConstructorProperties({"cs", "ms"})
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public CsMx(@NonNull final java.util.List<String> cs, @NonNull final MethodSelector ms) {
			if (cs == null) {
				throw new java.lang.NullPointerException("cs");
			}
			if (ms == null) {
				throw new java.lang.NullPointerException("ms");
			}
			this.cs = cs;
			this.ms = ms;
		}
		
		@NonNull
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public java.util.List<String> getCs() {
			return this.cs;
		}
		
		@NonNull
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public MethodSelector getMs() {
			return this.ms;
		}
		
		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public boolean equals(final java.lang.Object o) {
			if (o == this) return true;
			if (!(o instanceof Util.CsMx)) return false;
			final CsMx other = (CsMx)o;
			final java.lang.Object this$cs = this.getCs();
			final java.lang.Object other$cs = other.getCs();
			if (this$cs == null ? other$cs != null : !this$cs.equals(other$cs)) return false;
			final java.lang.Object this$ms = this.getMs();
			final java.lang.Object other$ms = other.getMs();
			if (this$ms == null ? other$ms != null : !this$ms.equals(other$ms)) return false;
			return true;
		}
		
		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int hashCode() {
			final int PRIME = 59;
			int result = 1;
			final java.lang.Object $cs = this.getCs();
			result = result * PRIME + ($cs == null ? 0 : $cs.hashCode());
			final java.lang.Object $ms = this.getMs();
			result = result * PRIME + ($ms == null ? 0 : $ms.hashCode());
			return result;
		}
		
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public CsMx withCs(@NonNull final java.util.List<String> cs) {
			if (cs == null) {
				throw new java.lang.NullPointerException("cs");
			}
			return this.cs == cs ? this : new CsMx(cs, this.ms);
		}
		
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public CsMx withMs(@NonNull final MethodSelector ms) {
			if (ms == null) {
				throw new java.lang.NullPointerException("ms");
			}
			return this.ms == ms ? this : new CsMx(this.cs, ms);
		}
	}
	
	public static final class PathMx {
		@NonNull
		private final Path path;
		@NonNull
		private final MethodSelector ms;
		
		public String toString() {
			return "" + path + "." + ms;
		}
		
		@java.beans.ConstructorProperties({"path", "ms"})
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public PathMx(@NonNull final Path path, @NonNull final MethodSelector ms) {
			if (path == null) {
				throw new java.lang.NullPointerException("path");
			}
			if (ms == null) {
				throw new java.lang.NullPointerException("ms");
			}
			this.path = path;
			this.ms = ms;
		}
		
		@NonNull
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public Path getPath() {
			return this.path;
		}
		
		@NonNull
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public MethodSelector getMs() {
			return this.ms;
		}
		
		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public boolean equals(final java.lang.Object o) {
			if (o == this) return true;
			if (!(o instanceof Util.PathMx)) return false;
			final PathMx other = (PathMx)o;
			final java.lang.Object this$path = this.getPath();
			final java.lang.Object other$path = other.getPath();
			if (this$path == null ? other$path != null : !this$path.equals(other$path)) return false;
			final java.lang.Object this$ms = this.getMs();
			final java.lang.Object other$ms = other.getMs();
			if (this$ms == null ? other$ms != null : !this$ms.equals(other$ms)) return false;
			return true;
		}
		
		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int hashCode() {
			final int PRIME = 59;
			int result = 1;
			final java.lang.Object $path = this.getPath();
			result = result * PRIME + ($path == null ? 0 : $path.hashCode());
			final java.lang.Object $ms = this.getMs();
			result = result * PRIME + ($ms == null ? 0 : $ms.hashCode());
			return result;
		}
		
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public PathMx withPath(@NonNull final Path path) {
			if (path == null) {
				throw new java.lang.NullPointerException("path");
			}
			return this.path == path ? this : new PathMx(path, this.ms);
		}
		
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public PathMx withMs(@NonNull final MethodSelector ms) {
			if (ms == null) {
				throw new java.lang.NullPointerException("ms");
			}
			return this.ms == ms ? this : new PathMx(this.path, ms);
		}
	}
	
	public static final class PathMxMx {
		@NonNull
		private final Path path;
		@NonNull
		private final MethodSelector ms1;
		@NonNull
		private final MethodSelector ms2;
		
		@java.beans.ConstructorProperties({"path", "ms1", "ms2"})
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public PathMxMx(@NonNull final Path path, @NonNull final MethodSelector ms1, @NonNull final MethodSelector ms2) {
			if (path == null) {
				throw new java.lang.NullPointerException("path");
			}
			if (ms1 == null) {
				throw new java.lang.NullPointerException("ms1");
			}
			if (ms2 == null) {
				throw new java.lang.NullPointerException("ms2");
			}
			this.path = path;
			this.ms1 = ms1;
			this.ms2 = ms2;
		}
		
		@NonNull
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public Path getPath() {
			return this.path;
		}
		
		@NonNull
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public MethodSelector getMs1() {
			return this.ms1;
		}
		
		@NonNull
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public MethodSelector getMs2() {
			return this.ms2;
		}
		
		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public boolean equals(final java.lang.Object o) {
			if (o == this) return true;
			if (!(o instanceof Util.PathMxMx)) return false;
			final PathMxMx other = (PathMxMx)o;
			final java.lang.Object this$path = this.getPath();
			final java.lang.Object other$path = other.getPath();
			if (this$path == null ? other$path != null : !this$path.equals(other$path)) return false;
			final java.lang.Object this$ms1 = this.getMs1();
			final java.lang.Object other$ms1 = other.getMs1();
			if (this$ms1 == null ? other$ms1 != null : !this$ms1.equals(other$ms1)) return false;
			final java.lang.Object this$ms2 = this.getMs2();
			final java.lang.Object other$ms2 = other.getMs2();
			if (this$ms2 == null ? other$ms2 != null : !this$ms2.equals(other$ms2)) return false;
			return true;
		}
		
		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int hashCode() {
			final int PRIME = 59;
			int result = 1;
			final java.lang.Object $path = this.getPath();
			result = result * PRIME + ($path == null ? 0 : $path.hashCode());
			final java.lang.Object $ms1 = this.getMs1();
			result = result * PRIME + ($ms1 == null ? 0 : $ms1.hashCode());
			final java.lang.Object $ms2 = this.getMs2();
			result = result * PRIME + ($ms2 == null ? 0 : $ms2.hashCode());
			return result;
		}
		
		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public java.lang.String toString() {
			return "Util.PathMxMx(path=" + this.getPath() + ", ms1=" + this.getMs1() + ", ms2=" + this.getMs2() + ")";
		}
		
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public PathMxMx withPath(@NonNull final Path path) {
			if (path == null) {
				throw new java.lang.NullPointerException("path");
			}
			return this.path == path ? this : new PathMxMx(path, this.ms1, this.ms2);
		}
		
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public PathMxMx withMs1(@NonNull final MethodSelector ms1) {
			if (ms1 == null) {
				throw new java.lang.NullPointerException("ms1");
			}
			return this.ms1 == ms1 ? this : new PathMxMx(this.path, ms1, this.ms2);
		}
		
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public PathMxMx withMs2(@NonNull final MethodSelector ms2) {
			if (ms2 == null) {
				throw new java.lang.NullPointerException("ms2");
			}
			return this.ms2 == ms2 ? this : new PathMxMx(this.path, this.ms1, ms2);
		}
	}
	
	public static final class PathPath {
		@NonNull
		private final Path path1;
		@NonNull
		private final Path path2;
		
		public String toString() {
			return "" + path1 + "->" + path2;
		}
		
		@java.beans.ConstructorProperties({"path1", "path2"})
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public PathPath(@NonNull final Path path1, @NonNull final Path path2) {
			if (path1 == null) {
				throw new java.lang.NullPointerException("path1");
			}
			if (path2 == null) {
				throw new java.lang.NullPointerException("path2");
			}
			this.path1 = path1;
			this.path2 = path2;
		}
		
		@NonNull
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public Path getPath1() {
			return this.path1;
		}
		
		@NonNull
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public Path getPath2() {
			return this.path2;
		}
		
		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public boolean equals(final java.lang.Object o) {
			if (o == this) return true;
			if (!(o instanceof Util.PathPath)) return false;
			final PathPath other = (PathPath)o;
			final java.lang.Object this$path1 = this.getPath1();
			final java.lang.Object other$path1 = other.getPath1();
			if (this$path1 == null ? other$path1 != null : !this$path1.equals(other$path1)) return false;
			final java.lang.Object this$path2 = this.getPath2();
			final java.lang.Object other$path2 = other.getPath2();
			if (this$path2 == null ? other$path2 != null : !this$path2.equals(other$path2)) return false;
			return true;
		}
		
		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int hashCode() {
			final int PRIME = 59;
			int result = 1;
			final java.lang.Object $path1 = this.getPath1();
			result = result * PRIME + ($path1 == null ? 0 : $path1.hashCode());
			final java.lang.Object $path2 = this.getPath2();
			result = result * PRIME + ($path2 == null ? 0 : $path2.hashCode());
			return result;
		}
		
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public PathPath withPath1(@NonNull final Path path1) {
			if (path1 == null) {
				throw new java.lang.NullPointerException("path1");
			}
			return this.path1 == path1 ? this : new PathPath(path1, this.path2);
		}
		
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public PathPath withPath2(@NonNull final Path path2) {
			if (path2 == null) {
				throw new java.lang.NullPointerException("path2");
			}
			return this.path2 == path2 ? this : new PathPath(this.path1, path2);
		}
	}
	
	public static class CachedStage {
		@NonNull
		ast.Ast.Stage stage = Stage.None;
		final java.util.Map<Path, ClassB> dependencies = new java.util.HashMap<>();
		final java.util.List<Path> allSupertypes = new java.util.ArrayList<>();
		boolean verified = false;
		
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public CachedStage() {
		}
		
		@NonNull
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public ast.Ast.Stage getStage() {
			return this.stage;
		}
		
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public java.util.Map<Path, ClassB> getDependencies() {
			return this.dependencies;
		}
		
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public java.util.List<Path> getAllSupertypes() {
			return this.allSupertypes;
		}
		
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public boolean isVerified() {
			return this.verified;
		}
		
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public void setStage(@NonNull final ast.Ast.Stage stage) {
			if (stage == null) {
				throw new java.lang.NullPointerException("stage");
			}
			this.stage = stage;
		}
		
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public void setVerified(final boolean verified) {
			this.verified = verified;
		}
		
		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public boolean equals(final java.lang.Object o) {
			if (o == this) return true;
			if (!(o instanceof Util.CachedStage)) return false;
			final CachedStage other = (CachedStage)o;
			if (!other.canEqual((java.lang.Object)this)) return false;
			final java.lang.Object this$stage = this.getStage();
			final java.lang.Object other$stage = other.getStage();
			if (this$stage == null ? other$stage != null : !this$stage.equals(other$stage)) return false;
			final java.lang.Object this$dependencies = this.getDependencies();
			final java.lang.Object other$dependencies = other.getDependencies();
			if (this$dependencies == null ? other$dependencies != null : !this$dependencies.equals(other$dependencies)) return false;
			final java.lang.Object this$allSupertypes = this.getAllSupertypes();
			final java.lang.Object other$allSupertypes = other.getAllSupertypes();
			if (this$allSupertypes == null ? other$allSupertypes != null : !this$allSupertypes.equals(other$allSupertypes)) return false;
			if (this.isVerified() != other.isVerified()) return false;
			return true;
		}
		
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		protected boolean canEqual(final java.lang.Object other) {
			return other instanceof Util.CachedStage;
		}
		
		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int hashCode() {
			final int PRIME = 59;
			int result = 1;
			final java.lang.Object $stage = this.getStage();
			result = result * PRIME + ($stage == null ? 0 : $stage.hashCode());
			final java.lang.Object $dependencies = this.getDependencies();
			result = result * PRIME + ($dependencies == null ? 0 : $dependencies.hashCode());
			final java.lang.Object $allSupertypes = this.getAllSupertypes();
			result = result * PRIME + ($allSupertypes == null ? 0 : $allSupertypes.hashCode());
			result = result * PRIME + (this.isVerified() ? 79 : 97);
			return result;
		}
		
		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public java.lang.String toString() {
			return "Util.CachedStage(stage=" + this.getStage() + ", dependencies=" + this.getDependencies() + ", allSupertypes=" + this.getAllSupertypes() + ", verified=" + this.isVerified() + ")";
		}
	}
	
	public static class CachedMt {
		ast.Ast.MethodType mt;
		Path path;
		
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public CachedMt() {
		}
		
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public ast.Ast.MethodType getMt() {
			return this.mt;
		}
		
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public Path getPath() {
			return this.path;
		}
		
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public void setMt(final ast.Ast.MethodType mt) {
			this.mt = mt;
		}
		
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public void setPath(final Path path) {
			this.path = path;
		}
		
		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public boolean equals(final java.lang.Object o) {
			if (o == this) return true;
			if (!(o instanceof Util.CachedMt)) return false;
			final CachedMt other = (CachedMt)o;
			if (!other.canEqual((java.lang.Object)this)) return false;
			final java.lang.Object this$mt = this.getMt();
			final java.lang.Object other$mt = other.getMt();
			if (this$mt == null ? other$mt != null : !this$mt.equals(other$mt)) return false;
			final java.lang.Object this$path = this.getPath();
			final java.lang.Object other$path = other.getPath();
			if (this$path == null ? other$path != null : !this$path.equals(other$path)) return false;
			return true;
		}
		
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		protected boolean canEqual(final java.lang.Object other) {
			return other instanceof Util.CachedMt;
		}
		
		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int hashCode() {
			final int PRIME = 59;
			int result = 1;
			final java.lang.Object $mt = this.getMt();
			result = result * PRIME + ($mt == null ? 0 : $mt.hashCode());
			final java.lang.Object $path = this.getPath();
			result = result * PRIME + ($path == null ? 0 : $path.hashCode());
			return result;
		}
		
		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public java.lang.String toString() {
			return "Util.CachedMt(mt=" + this.getMt() + ", path=" + this.getPath() + ")";
		}
	}
}